package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.repository.DroneRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DroneSerialNumberValidatorTest {

    @Autowired
    private DroneSerialNumberValidator droneSerialNumberValidator;

    @MockBean
    private DroneRepository droneRepository;

    @Test
    public void validate_whenDroneIsPresent_thenReturnResponseIncludingDrone() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder().build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .build();
        when(droneRepository.findById(loadDroneRequest.getDroneSerialNumber())).thenReturn(Optional.of(drone));

        droneSerialNumberValidator.validate(loadDroneRequest, loadDroneChainResponse);

        verify(droneRepository).findById(loadDroneRequest.getDroneSerialNumber());
        assertEquals(loadDroneChainResponse.getDrone(), drone);
    }


    @Test
    public void validate_whenDroneIsNotPresent_thenReturnValidationException() {
        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder().build();

        when(droneRepository.findById(loadDroneRequest.getDroneSerialNumber())).thenReturn(Optional.empty());

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> droneSerialNumberValidator.validate(loadDroneRequest, loadDroneChainResponse));

        assertEquals("Drone not found", validationException.getMessage());

        verify(droneRepository).findById(loadDroneRequest.getDroneSerialNumber());
    }
}
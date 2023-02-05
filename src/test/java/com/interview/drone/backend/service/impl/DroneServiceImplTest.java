package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.RegisterDroneRequest;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.entity.DroneModel;
import com.interview.drone.backend.entity.DroneState;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.DroneService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DroneServiceImplTest {

    @Autowired
    private DroneService droneService;

    @MockBean
    private DroneRepository droneRepository;

    @Test
    public void registerDrone_whenDroneIsNotPresent_thenReturnDroneResponse() {
        RegisterDroneRequest request = getRegisterDroneRequest();
        Drone savedDrone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .weightLimitInGram(500)
                .batteryCapacity(100)
                .droneState(DroneState.IDLE)
                .build();
        when(droneRepository.save(any(Drone.class))).thenReturn(savedDrone);

        DroneResponse response = droneService.registerDrone(request);

        assertEquals(response.getSerialNumber(), savedDrone.getSerialNumber());
        assertEquals(response.getBatteryCapacity(), savedDrone.getBatteryCapacity());
        assertEquals(response.getDroneModel(), savedDrone.getDroneModel());
        assertEquals(response.getWeightLimit(), savedDrone.getWeightLimitInGram());
        assertEquals(response.getDroneState().toString(), savedDrone.getDroneState().toString());
    }

    @Test
    public void registerDrone_whenDroneIsAlreadyPresent_thenThrowValidationException() {
        RegisterDroneRequest request = getRegisterDroneRequest();
        when(droneRepository.findById("123")).thenReturn(java.util.Optional.of(new Drone()));

        assertThrows(ValidationException.class, () -> droneService.registerDrone(request));
    }

    private static RegisterDroneRequest getRegisterDroneRequest() {
        return RegisterDroneRequest.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .weightLimitInGram(500)
                .batteryCapacity(100)
                .build();
    }
}
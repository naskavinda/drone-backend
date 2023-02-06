package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.entity.DroneModel;
import com.interview.drone.backend.entity.DroneState;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AllowedDroneStatusValidatorTest {

    @Autowired
    private AllowedDroneStatusValidator allowedDroneStatusValidator;

    @Test
    public void validate_whenAllowedDroneStateIsPresent_thenReturnSameLoadDroneChainResponse() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .droneState(DroneState.IDLE)
                .batteryCapacity(100)
                .weightLimitInGram(500)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder()
                .drone(drone)
                .build();

        LoadDroneChainResponse validatedResponse = allowedDroneStatusValidator.validate(loadDroneRequest, loadDroneChainResponse);

        assertEquals(loadDroneChainResponse, validatedResponse);
    }

    @Test
    public void validate_whenAllowedDroneStateIsNotPresent_thenReturnValidationException() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .droneState(DroneState.DELIVERING)
                .batteryCapacity(100)
                .weightLimitInGram(500)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder()
                .drone(drone)
                .build();

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> allowedDroneStatusValidator.validate(loadDroneRequest, loadDroneChainResponse));

        assertEquals("Can not load Medication to the this drone", validationException.getMessage());
    }

}
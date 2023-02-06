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
class BatteryLevelValidatorTest {

    @Autowired
    private BatteryLevelValidator batteryLevelValidator;

    @Test
    public void validate_whenBatteryCapacityIsHigherThenMin_thenReturnSameLoadDroneChainResponse() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .droneState(DroneState.IDLE)
                .batteryCapacity(80)
                .weightLimitInGram(500)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder()
                .drone(drone)
                .build();

        LoadDroneChainResponse validatedResponse = batteryLevelValidator.validate(loadDroneRequest, loadDroneChainResponse);

        assertEquals(loadDroneChainResponse, validatedResponse);
    }

    @Test
    public void validate_whenBatteryCapacityIsEqualMin_thenReturnSameLoadDroneChainResponse() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .droneState(DroneState.IDLE)
                .batteryCapacity(25)
                .weightLimitInGram(500)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder()
                .drone(drone)
                .build();

        LoadDroneChainResponse validatedResponse = batteryLevelValidator.validate(loadDroneRequest, loadDroneChainResponse);

        assertEquals(loadDroneChainResponse, validatedResponse);
    }

    @Test
    public void validate_whenBatteryCapacityIsLessThanMin_thenReturnValidationException() {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber("123")
                .build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .droneState(DroneState.IDLE)
                .batteryCapacity(24)
                .weightLimitInGram(500)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder()
                .drone(drone)
                .build();

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> batteryLevelValidator.validate(loadDroneRequest, loadDroneChainResponse));

        assertEquals("Battery level must be equal or higher that 25%", validationException.getMessage());
    }
}
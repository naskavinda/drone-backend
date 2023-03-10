package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.BATTERY_LEVEL_VALIDATOR_ORDER;

@Component
public class BatteryLevelValidator extends LoadDroneValidator {

    public static final int ALLOWED_MIN_BATTERY_CAPACITY = 25;

    @Override
    public LoadDroneChainResponse validate(LoadDroneRequest loadDroneRequest, LoadDroneChainResponse loadDroneChainResponse) {
        int batteryCapacity = loadDroneChainResponse.getDrone().getBatteryCapacity();
        if (batteryCapacity < ALLOWED_MIN_BATTERY_CAPACITY) {
            throw new ValidationException("Battery level must be equal or higher that 25%");
        }
        return validateNext(loadDroneRequest, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return BATTERY_LEVEL_VALIDATOR_ORDER;
    }
}

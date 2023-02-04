package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneDTO;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class BatteryLevelValidator extends LoadDroneValidator {

    public static final int ALLOWED_MIN_BATTERY_CAPACITY = 25;

    @Override
    public LoadDroneChainResponse validate(LoadDroneDTO loadDroneDTO, LoadDroneChainResponse loadDroneChainResponse) {
        int batteryCapacity = loadDroneChainResponse.getDrone().getBatteryCapacity();
        if (batteryCapacity < ALLOWED_MIN_BATTERY_CAPACITY) {
            throw new ValidationException("Battery level must be above 25%");
        }
        return validateNext(loadDroneDTO, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}

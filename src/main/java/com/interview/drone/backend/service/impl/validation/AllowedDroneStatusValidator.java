package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.entity.DroneState;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.ALLOWED_DRONE_STATUS_VALIDATOR_ORDER;

@Component
public class AllowedDroneStatusValidator extends LoadDroneValidator {

    public static final List<DroneState> LOADING_ALLOWED_DRONE_STATES = List.of(DroneState.IDLE, DroneState.LOADING);

    @Override
    public LoadDroneChainResponse validate(LoadDroneDTO loadDroneDTO, LoadDroneChainResponse loadDroneChainResponse) {
        if (!LOADING_ALLOWED_DRONE_STATES.contains(loadDroneChainResponse.getDrone().getDroneState())) {
            throw new ValidationException("Can not load Medication to the this drone.");
        }
        return validateNext(loadDroneDTO, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return ALLOWED_DRONE_STATUS_VALIDATOR_ORDER;
    }
}

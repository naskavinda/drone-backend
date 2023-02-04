package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.repository.DroneRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.DRONE_SERIAL_NUMBER_VALIDATOR_ORDER;

@Component
public class DroneSerialNumberValidator extends LoadDroneValidator {

    private final DroneRepository droneRepository;

    public DroneSerialNumberValidator(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public LoadDroneChainResponse validate(LoadDroneRequest loadDroneRequest, LoadDroneChainResponse loadDroneChainResponse) {
        Drone drone = droneRepository.findById(loadDroneRequest.getDroneSerialNumber())
                .orElseThrow(() -> new ValidationException("Drone not found"));
        loadDroneChainResponse.setDrone(drone);
        return validateNext(loadDroneRequest, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return DRONE_SERIAL_NUMBER_VALIDATOR_ORDER;
    }
}

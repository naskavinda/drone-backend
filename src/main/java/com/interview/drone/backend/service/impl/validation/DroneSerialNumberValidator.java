package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.repository.DroneRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DroneSerialNumberValidator extends LoadDroneValidator {

    private final DroneRepository droneRepository;

    public DroneSerialNumberValidator(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public LoadDroneChainResponse validate(LoadDroneDTO loadDroneDTO, LoadDroneChainResponse loadDroneChainResponse) {
        Drone drone = droneRepository.findById(loadDroneDTO.getDroneSerialNumber())
                .orElseThrow(() -> new ValidationException("Drone not found"));
        loadDroneChainResponse.setDrone(drone);
        return validateNext(loadDroneDTO, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

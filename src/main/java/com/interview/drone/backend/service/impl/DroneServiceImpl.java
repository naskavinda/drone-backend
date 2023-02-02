package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.DroneService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    public DroneServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public Drone registerDrone(Drone drone) {
        boolean droneIsAlreadyPresent = droneRepository.findById(drone.getSerialNumber()).isPresent();
        if (droneIsAlreadyPresent) {
            throw new ValidationException("Drone is already exist");
        }
        return droneRepository.save(drone);
    }
}

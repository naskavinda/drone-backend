package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.DroneService;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    public DroneServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public String registerDrone(Drone drone) {
        Drone save = droneRepository.save(drone);
        return save.getSerialNumber();
    }
}

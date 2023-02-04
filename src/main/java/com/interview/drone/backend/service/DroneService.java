package com.interview.drone.backend.service;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.entity.Drone;

public interface DroneService {
    Drone registerDrone(Drone drone);

    void loadMedicationToDrone(LoadDroneDTO loadDrone);
}

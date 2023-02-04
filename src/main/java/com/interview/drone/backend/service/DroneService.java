package com.interview.drone.backend.service;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.dto.LoadedMedicationResponse;
import com.interview.drone.backend.entity.Drone;

import java.util.List;

public interface DroneService {
    Drone registerDrone(Drone drone);

    void loadMedicationToDrone(LoadDroneDTO loadDrone);

    List<LoadedMedicationResponse> getMedicationByDrone(String serialNumber);
}

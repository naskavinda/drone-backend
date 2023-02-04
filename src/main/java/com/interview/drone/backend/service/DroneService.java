package com.interview.drone.backend.service;

import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.dto.LoadedMedicationResponse;
import com.interview.drone.backend.dto.RegisterDroneRequest;

import java.util.List;

public interface DroneService {
    DroneResponse registerDrone(RegisterDroneRequest drone);

    void loadMedicationToDrone(LoadDroneRequest loadDrone);

    List<LoadedMedicationResponse> getMedicationByDrone(String serialNumber);

    List<DroneResponse> getAvailableDrones();

    double getBatteryLevelByDrone(String serialNumber);
}

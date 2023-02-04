package com.interview.drone.backend.dto;

import com.interview.drone.backend.entity.DroneModel;
import com.interview.drone.backend.entity.DroneState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneResponse {
    private String serialNumber;
    private DroneState droneState;
    private int batteryCapacity;
    private DroneModel droneModel;
    private double weightLimit;
}

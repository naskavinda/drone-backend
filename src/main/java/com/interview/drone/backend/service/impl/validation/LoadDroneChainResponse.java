package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.entity.Address;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneChainResponse {

    private Drone drone;
    private List<Medication> medications;
    private double totalWeight;
    private Address address;

}

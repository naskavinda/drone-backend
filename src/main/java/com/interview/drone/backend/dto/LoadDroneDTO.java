package com.interview.drone.backend.dto;

import com.interview.drone.backend.validation.AllowedConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneDTO {

    private String DroneSerialNumber;
    private Integer addressId;
    @AllowedConstraint(allowedValues = {"LOADING", "LOADED"}, message = "Drone State must be LOADING or LOADED.")
    @NotNull(message = "Drone State can not be Null")
    @NotBlank(message = "Drone State can not be blank")
    private String droneState;
    private List<MedicationDTO> medications;
}

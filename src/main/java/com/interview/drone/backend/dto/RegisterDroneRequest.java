package com.interview.drone.backend.dto;

import com.interview.drone.backend.entity.DroneModel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDroneRequest {

    @Size(max = 100, message = "Serial Number max length is 100")
    @NotBlank(message = "Serial Number can not be Blank")
    @NotNull(message = "Serial Number can not be Null")
    private String serialNumber;

    @NotNull(message = "Drone Model can not be Null")
    private DroneModel droneModel;

    @Max(value = 500, message = "Weight limit max value is 500g")
    @Min(value = 0, message = "Weight can not be less then 0")
    private double weightLimitInGram;

    @Max(value = 100, message = "Battery Capacity max value is 100")
    @Min(value = 0, message = "Battery Capacity min value is 0")
    private int batteryCapacity;

}

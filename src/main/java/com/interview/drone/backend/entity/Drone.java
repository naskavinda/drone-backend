package com.interview.drone.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @Column(length = 100, unique = true)
    @Size(max = 100, message = "Serial Number max length is 100")
    @NotBlank(message = "Serial Number can not be Blank")
    @NotNull(message = "Serial Number can not be Null")
    private String serialNumber;

    @Convert(converter = DroneModelConverter.class)
    @NotNull(message = "Drone Model can not be Null")
    private DroneModel droneModel;

    @Max(value = 500, message = "Weight limit max value is 500g")
    private double weightLimitInGram;

    @Max(value = 100, message = "Battery Capacity max value is 100")
    @Min(value = 0, message = "Battery Capacity min value is 0")
    private int batteryCapacity;

    @Convert(converter = DroneStateConverter.class)
    @NotNull(message = "Drone State can not be Null")
    private DroneState droneState;

    @JsonIgnore
    @OneToMany(mappedBy = "drone")
    Set<DeliveryDetails> deliveryDetails;

    private static class DroneModelConverter implements AttributeConverter<DroneModel, Integer> {
        @Override
        public Integer convertToDatabaseColumn(DroneModel droneModel) {
            return droneModel.getId();
        }

        @Override
        public DroneModel convertToEntityAttribute(Integer droneModelValue) {
            return DroneModel.valueOf(droneModelValue);
        }
    }

    private static class DroneStateConverter implements AttributeConverter<DroneState, Integer> {
        @Override
        public Integer convertToDatabaseColumn(DroneState droneState) {
            return droneState.getId();
        }

        @Override
        public DroneState convertToEntityAttribute(Integer droneStateValue) {
            return DroneState.valueOf(droneStateValue);
        }
    }
}

package com.interview.drone.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @Column(length = 100, unique = true)
    private String serialNumber;

    @Convert(converter = DroneModelConverter.class)
    private DroneModel droneModel;

    private double weightLimitInGram;

    private int batteryCapacity;

    @Convert(converter = DroneStateConverter.class)
    private DroneState droneState;

    @OneToMany(mappedBy = "drone")
    Set<Delivery> deliveries;

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

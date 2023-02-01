package com.interview.drone.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneHasMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "serialNumber")
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "medicationId")
    private Medication medication;

    private LocalDateTime orderDateTime;
    private Integer medicationQty;

}

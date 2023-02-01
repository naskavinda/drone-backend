package com.interview.drone.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicationId;
    private String name;
    private double weight;
    private String code;
    private String imageUrl;

    @OneToMany(mappedBy = "medication")
    Set<DeliveryDetails> deliveryDetails;
}

package com.interview.drone.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryDetailsId;

    private Double medicationQty;

    @ManyToOne
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "code")
    private Medication medication;

}

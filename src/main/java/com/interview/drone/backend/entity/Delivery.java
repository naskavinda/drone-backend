package com.interview.drone.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryId;

    private double totalWeight;

    @ManyToOne
    @JoinColumn(name = "serialNumber")
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "addressId")
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "delivery")
    private Set<DeliveryDetails> deliveryDetails;

}

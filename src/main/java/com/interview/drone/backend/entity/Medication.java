package com.interview.drone.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "", message = "Name can have only upper case letters, dash(-), underscore(_) and numbers")
    @NotBlank(message = "Name can not be Blank")
    @NotNull(message = "Name can not be Null")
    private String name;

    private double weight;

    @Pattern(regexp = "", message = "Code can have only upper case letters, underscore(_) and numbers")
    @NotBlank(message = "Code can not be Blank")
    @NotNull(message = "Code can not be Null")
    private String code;

    @NotBlank(message = "Image can not be Blank")
    @NotNull(message = "Image can not be Null")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "medication")
    Set<DeliveryDetails> deliveryDetails;
}

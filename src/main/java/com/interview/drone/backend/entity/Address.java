package com.interview.drone.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @NotNull(message = "Address line 1 can not be Null")
    @NotBlank(message = "Address line 1 can not be Blank")
    private String line1;

    private String line2;


    @NotNull(message = "City can not be Null")
    @NotBlank(message = "City can not be Blank")
    private String city;

    private String country;

    @NotNull(message = "Longitude can not be Null")
    @NotBlank(message = "Longitude can not be Blank")
    private String longitude;

    @NotNull(message = "Latitude can not be Null")
    @NotBlank(message = "Latitude can not be Blank")
    private String latitude;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private Set<Delivery> deliveries;
}

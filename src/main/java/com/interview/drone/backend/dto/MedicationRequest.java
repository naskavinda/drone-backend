package com.interview.drone.backend.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationRequest {
    @NotNull(message = "Medication code Can not be Null")
    @NotBlank(message = "Medication code Can not be Blank")
    private String medicationCode;
    @Min(value = 1, message = "Medication quantity minimum value is 1")
    private double medicationQty;
}

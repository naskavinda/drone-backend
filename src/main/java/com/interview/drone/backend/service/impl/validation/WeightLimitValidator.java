package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.dto.MedicationDTO;
import com.interview.drone.backend.entity.Medication;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.WEIGHT_LIMIT_VALIDATOR_ORDER;

@Component
public class WeightLimitValidator extends LoadDroneValidator{
    @Override
    public LoadDroneChainResponse validate(LoadDroneDTO loadDroneDTO, LoadDroneChainResponse loadDroneChainResponse) {
        double totalWeight = calculateTotalWeight(loadDroneDTO.getMedications(), loadDroneChainResponse.getMedications());
        if (totalWeight > loadDroneChainResponse.getDrone().getWeightLimitInGram()) {
            throw new ValidationException("Total Weight can not exceed the weight limit of the drone");
        }
        return validateNext(loadDroneDTO, loadDroneChainResponse);
    }

    private double calculateTotalWeight(List<MedicationDTO> medications, List<Medication> medicationList) {
        return medications.stream()
                .mapToDouble(medication -> medication.getMedicationQty() * getMedication(medicationList, medication).getWeight())
                .sum();
    }

    private static Medication getMedication(List<Medication> medications, MedicationDTO medicationDTO) {
        return medications.stream()
                .filter(medication -> Objects.equals(medication.getCode(), medicationDTO.getMedicationCode()))
                .findFirst()
                .get();
    }

    @Override
    public int getOrder() {
        return WEIGHT_LIMIT_VALIDATOR_ORDER;
    }
}

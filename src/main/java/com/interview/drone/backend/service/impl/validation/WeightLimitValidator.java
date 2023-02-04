package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.dto.MedicationRequest;
import com.interview.drone.backend.entity.Medication;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.WEIGHT_LIMIT_VALIDATOR_ORDER;

@Component
public class WeightLimitValidator extends LoadDroneValidator{
    @Override
    public LoadDroneChainResponse validate(LoadDroneRequest loadDroneRequest, LoadDroneChainResponse loadDroneChainResponse) {
        double totalWeight = calculateTotalWeight(loadDroneRequest.getMedications(), loadDroneChainResponse.getMedications());
        if (totalWeight > loadDroneChainResponse.getDrone().getWeightLimitInGram()) {
            throw new ValidationException("Total Weight can not exceed the weight limit of the drone");
        }
        loadDroneChainResponse.setTotalWeight(totalWeight);
        return validateNext(loadDroneRequest, loadDroneChainResponse);
    }

    private double calculateTotalWeight(List<MedicationRequest> medications, List<Medication> medicationList) {
        return medications.stream()
                .mapToDouble(medication -> medication.getMedicationQty() * getMedication(medicationList, medication).getWeight())
                .sum();
    }

    private static Medication getMedication(List<Medication> medications, MedicationRequest medicationRequest) {
        return medications.stream()
                .filter(medication -> Objects.equals(medication.getCode(), medicationRequest.getMedicationCode()))
                .findFirst()
                .get();
    }

    @Override
    public int getOrder() {
        return WEIGHT_LIMIT_VALIDATOR_ORDER;
    }
}

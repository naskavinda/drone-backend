package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.dto.MedicationDTO;
import com.interview.drone.backend.entity.Medication;
import com.interview.drone.backend.repository.MedicationRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.interview.drone.backend.service.impl.validation.LoadDroneValidatorChainOrder.MEDICATION_CODE_LIST_VALIDATOR_ORDER;

@Component
public class MedicationCodeListValidator extends LoadDroneValidator {

    private final MedicationRepository medicationRepository;

    public MedicationCodeListValidator(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public LoadDroneChainResponse validate(LoadDroneDTO loadDroneDTO, LoadDroneChainResponse loadDroneChainResponse) {
        List<String> medicationList = loadDroneDTO.getMedications().stream().map(MedicationDTO::getMedicationCode).toList();
        List<Medication> medications = medicationRepository.findByCodeIn(medicationList);
        if (medications.size() != loadDroneDTO.getMedications().size()) {
            throw new ValidationException("Medications can not find in the Database");
        }
        loadDroneChainResponse.setMedications(medications);
        return validateNext(loadDroneDTO, loadDroneChainResponse);
    }

    @Override
    public int getOrder() {
        return MEDICATION_CODE_LIST_VALIDATOR_ORDER;
    }
}

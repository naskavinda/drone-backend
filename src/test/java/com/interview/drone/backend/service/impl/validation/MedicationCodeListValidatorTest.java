package com.interview.drone.backend.service.impl.validation;

import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.dto.MedicationRequest;
import com.interview.drone.backend.entity.Medication;
import com.interview.drone.backend.repository.MedicationRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MedicationCodeListValidatorTest {

    @Autowired
    private MedicationCodeListValidator medicationCodeListValidator;

    @MockBean
    private MedicationRepository medicationRepository;

    @Test
    public void validate_whenMedicationIsPresentInDB_thenReturnLoadDroneChainResponseIncludingMedication() {

        MedicationRequest medicationRequest1 = MedicationRequest.builder()
                .medicationCode("MD-1")
                .medicationQty(2)
                .build();
        MedicationRequest medicationRequest2 = MedicationRequest.builder()
                .medicationCode("MD-2")
                .medicationQty(3)
                .build();

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .medications(List.of(medicationRequest1, medicationRequest2))
                .build();

        Medication medication1 = Medication.builder()
                .weight(20)
                .code("MD-1")
                .name("ABC")
                .imageUrl("www.google.com/abc")
                .build();
        Medication medication2 = Medication.builder()
                .weight(80)
                .code("MD-2")
                .name("second")
                .imageUrl("www.google.com/second")
                .build();

        List<Medication> medicationList = List.of(medication1, medication2);

        List<String> codeList = List.of("MD-1", "MD-2");
        when(medicationRepository.findByCodeIn(codeList)).thenReturn(medicationList);

        LoadDroneChainResponse validatedResponse = medicationCodeListValidator.validate(loadDroneRequest, LoadDroneChainResponse.builder().build());

        assertEquals(validatedResponse.getMedications(), medicationList);
        verify(medicationRepository).findByCodeIn(codeList);
    }

    @Test
    public void validate_whenMedicationIsNotPresentInDB_thenReturnValidationException() {

        MedicationRequest medicationRequest1 = MedicationRequest.builder()
                .medicationCode("MD-1")
                .medicationQty(2)
                .build();
        MedicationRequest medicationRequest2 = MedicationRequest.builder()
                .medicationCode("MD-2")
                .medicationQty(3)
                .build();

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .medications(List.of(medicationRequest1, medicationRequest2))
                .build();

        Medication medication1 = Medication.builder()
                .weight(20)
                .code("MD-1")
                .name("ABC")
                .imageUrl("www.google.com/abc")
                .build();

        List<Medication> medicationList = List.of(medication1);

        List<String> codeList = List.of("MD-1", "MD-2");
        when(medicationRepository.findByCodeIn(codeList)).thenReturn(medicationList);

        ValidationException validatedResponse = assertThrows(ValidationException.class,
                () -> medicationCodeListValidator.validate(loadDroneRequest, LoadDroneChainResponse.builder().build()));

        assertEquals("Medications can not find in the Database", validatedResponse.getMessage());
    }
}
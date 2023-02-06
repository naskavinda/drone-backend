package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.dto.MedicationRequest;
import com.interview.drone.backend.dto.RegisterDroneRequest;
import com.interview.drone.backend.entity.*;
import com.interview.drone.backend.repository.DeliveryDetailsRepository;
import com.interview.drone.backend.repository.DeliveryRepository;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.DroneService;
import com.interview.drone.backend.service.impl.validation.DroneSerialNumberValidator;
import com.interview.drone.backend.service.impl.validation.LoadDroneChainResponse;
import com.interview.drone.backend.service.impl.validation.LoadDroneValidator;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DroneServiceImplTest {

    @Autowired
    private DroneService droneService;

    @MockBean
    private DroneRepository droneRepository;

    @MockBean
    private DeliveryRepository deliveryRepository;

    @MockBean
    private DeliveryDetailsRepository deliveryDetailsRepository;

    @MockBean
    private List<LoadDroneValidator> loadDroneValidators;

    @MockBean
    private DroneSerialNumberValidator droneSerialNumberValidator;


    @Test
    public void registerDrone_whenDroneIsNotPresent_thenReturnDroneResponse() {
        RegisterDroneRequest request = getRegisterDroneRequest();
        Drone savedDrone = Drone.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .weightLimitInGram(500)
                .batteryCapacity(100)
                .droneState(DroneState.IDLE)
                .build();
        when(droneRepository.save(any(Drone.class))).thenReturn(savedDrone);

        DroneResponse response = droneService.registerDrone(request);

        assertEquals(response.getSerialNumber(), savedDrone.getSerialNumber());
        assertEquals(response.getBatteryCapacity(), savedDrone.getBatteryCapacity());
        assertEquals(response.getDroneModel(), savedDrone.getDroneModel());
        assertEquals(response.getWeightLimit(), savedDrone.getWeightLimitInGram());
        assertEquals(response.getDroneState().toString(), savedDrone.getDroneState().toString());
    }

    @Test
    public void registerDrone_whenDroneIsAlreadyPresent_thenThrowValidationException() {
        RegisterDroneRequest request = getRegisterDroneRequest();
        when(droneRepository.findById("123")).thenReturn(java.util.Optional.of(new Drone()));

        assertThrows(ValidationException.class, () -> droneService.registerDrone(request));
    }

    @Test
    public void loadMedicationToDrone_whenLoadDroneRequestIsValid_thenSuccessfullyLoadDrone() {
        LoadDroneRequest request = LoadDroneRequest.builder()
                .medications(List.of(MedicationRequest.builder().medicationQty(3).medicationCode("MD-1").build()))
                .droneState(DroneState.LOADING.toString())
                .droneSerialNumber("222")
                .addressId(1)
                .build();

        Medication medication = Medication.builder()
                .code("MD-1")
                .imageUrl("http://www.google.com/my-image")
                .name("paracetamol")
                .weight(5)
                .build();

        Drone drone = Drone.builder()
                .serialNumber("123")
                .droneState(DroneState.IDLE)
                .weightLimitInGram(500)
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .batteryCapacity(100)
                .build();

        LoadDroneChainResponse loadDroneChainResponse = LoadDroneChainResponse.builder()
                .medications(List.of(medication))
                .drone(drone)
                .build();
        Delivery delivery = Delivery.builder()
                .drone(drone)
                .build();
        DeliveryDetails deliveryDetails = DeliveryDetails.builder()
                .delivery(delivery)
                .medicationQty(3.0)
                .medication(medication)
                .build();
        List<DeliveryDetails> deliveryDetailsList = List.of(deliveryDetails);

        when(loadDroneValidators.get(0)).thenReturn(droneSerialNumberValidator);
        when(loadDroneValidators.get(0).validate(request, LoadDroneChainResponse.builder().build())).thenReturn(loadDroneChainResponse);
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        when(deliveryDetailsRepository.saveAll(deliveryDetailsList)).thenReturn(deliveryDetailsList);
        when(droneRepository.save(drone)).thenReturn(drone);

        droneService.loadMedicationToDrone(request);

        verify(loadDroneValidators.get(0)).validate(request, LoadDroneChainResponse.builder().build());
        verify(deliveryRepository).save(delivery);
        verify(deliveryDetailsRepository).saveAll(deliveryDetailsList);
        verify(droneRepository).save(drone);
        assertEquals(DroneState.LOADING, drone.getDroneState());
    }

    private static RegisterDroneRequest getRegisterDroneRequest() {
        return RegisterDroneRequest.builder()
                .serialNumber("123")
                .droneModel(DroneModel.LIGHT_WEIGHT)
                .weightLimitInGram(500)
                .batteryCapacity(100)
                .build();
    }
}
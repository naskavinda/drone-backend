package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.dto.LoadedMedicationResponse;
import com.interview.drone.backend.dto.MedicationDTO;
import com.interview.drone.backend.entity.*;
import com.interview.drone.backend.repository.*;
import com.interview.drone.backend.service.DroneService;
import com.interview.drone.backend.service.impl.validation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final DeliveryDetailsRepository deliveryDetailsRepository;
    private final DeliveryRepository deliveryRepository;

    private final List<LoadDroneValidator> loadDroneValidators;

    public DroneServiceImpl(DroneRepository droneRepository,
                            DeliveryDetailsRepository deliveryDetailsRepository,
                            DeliveryRepository deliveryRepository,
                            List<LoadDroneValidator> loadDroneValidators) {
        this.droneRepository = droneRepository;
        this.deliveryDetailsRepository = deliveryDetailsRepository;
        this.deliveryRepository = deliveryRepository;
        this.loadDroneValidators = loadDroneValidators;
    }

    @Override
    public Drone registerDrone(Drone drone) {
        boolean droneIsAlreadyPresent = droneRepository.findById(drone.getSerialNumber()).isPresent();
        if (droneIsAlreadyPresent) {
            throw new ValidationException("Drone is already exist");
        }
        drone.setDroneState(DroneState.IDLE);
        return droneRepository.save(drone);
    }

    @Override
    @Transactional()
    public void loadMedicationToDrone(LoadDroneDTO loadDrone) {

        LoadDroneValidator droneValidator = loadDroneValidators.get(0);
        List<LoadDroneValidator> validators = loadDroneValidators.stream().skip(1).toList();
        droneValidator.link(validators);

        LoadDroneChainResponse validationResponse = droneValidator.validate(loadDrone, new LoadDroneChainResponse());

        Drone drone = validationResponse.getDrone();
        Delivery delivery = Delivery.builder()
                .address(validationResponse.getAddress())
                .drone(drone)
                .totalWeight(validationResponse.getTotalWeight())
                .build();
        Delivery deliverySaved = deliveryRepository.save(delivery);

        List<DeliveryDetails> deliveryDetails = loadDrone.getMedications().stream()
                .map(medicationDTO -> DeliveryDetails.builder()
                        .delivery(deliverySaved)
                        .medication(getMedication(validationResponse.getMedications(), medicationDTO))
                        .medicationQty(medicationDTO.getMedicationQty())
                        .build())
                .toList();
        deliveryDetailsRepository.saveAll(deliveryDetails);

        drone.setDroneState(DroneState.valueOf(loadDrone.getDroneState()));
        droneRepository.save(drone);
    }

    @Override
    public List<LoadedMedicationResponse> getMedicationByDrone(String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ValidationException("Drone not found"));

        List<DroneState> loadedDroneStates = List.of(DroneState.LOADING, DroneState.LOADED, DroneState.DELIVERING);
        if (!loadedDroneStates.contains(drone.getDroneState())) {
            throw new ValidationException("No Loaded medication items");
        }

        Delivery delivery = deliveryRepository.findTopByDroneSerialNumberOrderByDeliveryIdDesc(serialNumber);
        List<DeliveryDetails> deliveryDetails = deliveryDetailsRepository.findByDeliveryDeliveryId(delivery.getDeliveryId());
        return deliveryDetails.stream()
                .map(deliveryDetail -> LoadedMedicationResponse.builder()
                        .medicationCode(deliveryDetail.getMedication().getCode())
                        .medicationQty(deliveryDetail.getMedicationQty())
                        .build())
                .toList();
    }

    private static Medication getMedication(List<Medication> medications, MedicationDTO medicationDTO) {
        return medications.stream()
                .filter(medication -> Objects.equals(medication.getCode(), medicationDTO.getMedicationCode()))
                .findFirst()
                .get();
    }

}

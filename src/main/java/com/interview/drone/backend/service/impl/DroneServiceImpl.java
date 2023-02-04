package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.dto.*;
import com.interview.drone.backend.entity.*;
import com.interview.drone.backend.repository.DeliveryDetailsRepository;
import com.interview.drone.backend.repository.DeliveryRepository;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.DroneService;
import com.interview.drone.backend.service.impl.validation.LoadDroneChainResponse;
import com.interview.drone.backend.service.impl.validation.LoadDroneValidator;
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
    public DroneResponse registerDrone(RegisterDroneRequest registerDroneRequest) {
        boolean droneIsAlreadyPresent = droneRepository.findById(registerDroneRequest.getSerialNumber()).isPresent();
        if (droneIsAlreadyPresent) {
            throw new ValidationException("Drone is already exist");
        }
        Drone drone = Drone.builder()
                .serialNumber(registerDroneRequest.getSerialNumber())
                .batteryCapacity(registerDroneRequest.getBatteryCapacity())
                .droneModel(registerDroneRequest.getDroneModel())
                .weightLimitInGram(registerDroneRequest.getWeightLimitInGram())
                .droneState(DroneState.IDLE)
                .build();

        Drone savedDrone = droneRepository.save(drone);

        return mapToDroneResponse(savedDrone);
    }


    @Override
    @Transactional()
    public void loadMedicationToDrone(LoadDroneRequest loadDrone) {

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
                .map(medicationRequest -> DeliveryDetails.builder()
                        .delivery(deliverySaved)
                        .medication(getMedication(validationResponse.getMedications(), medicationRequest))
                        .medicationQty(medicationRequest.getMedicationQty())
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

        List<DroneState> loadedDroneStateList = List.of(DroneState.LOADING, DroneState.LOADED, DroneState.DELIVERING);
        if (!loadedDroneStateList.contains(drone.getDroneState())) {
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

    @Override
    public List<DroneResponse> getAvailableDrones() {
        List<DroneState> availableDroneStateList = List.of(DroneState.IDLE, DroneState.LOADING);
        List<Drone> droneList = droneRepository.findByDroneStateIn(availableDroneStateList);
        return droneList.stream()
                .map(DroneServiceImpl::mapToDroneResponse)
                .toList();
    }

    @Override
    public double getBatteryLevelByDrone(String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new ValidationException("Drone not found"));
        return drone.getBatteryCapacity();
    }

    private static Medication getMedication(List<Medication> medications, MedicationRequest medicationRequest) {
        return medications.stream()
                .filter(medication -> Objects.equals(medication.getCode(), medicationRequest.getMedicationCode()))
                .findFirst()
                .get();
    }

    private static DroneResponse mapToDroneResponse(Drone savedDrone) {
        return DroneResponse.builder()
                .serialNumber(savedDrone.getSerialNumber())
                .droneModel(savedDrone.getDroneModel())
                .droneState(savedDrone.getDroneState())
                .batteryCapacity(savedDrone.getBatteryCapacity())
                .weightLimit(savedDrone.getWeightLimitInGram())
                .build();
    }

}

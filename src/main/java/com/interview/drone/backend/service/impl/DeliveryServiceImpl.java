package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.repository.DeliveryDetailsRepository;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.repository.MedicationRepository;
import com.interview.drone.backend.service.DeliveryService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DeliveryDetailsRepository deliveryDetailsRepository;

    public DeliveryServiceImpl(DroneRepository droneRepository,
                               MedicationRepository medicationRepository,
                               DeliveryDetailsRepository deliveryDetailsRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.deliveryDetailsRepository = deliveryDetailsRepository;
    }
}

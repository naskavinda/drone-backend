package com.interview.drone.backend.repository;

import com.interview.drone.backend.entity.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneHasMedicationRepository extends JpaRepository<DeliveryDetails, Integer> {
}

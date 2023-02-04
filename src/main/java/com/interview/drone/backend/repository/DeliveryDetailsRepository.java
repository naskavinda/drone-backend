package com.interview.drone.backend.repository;

import com.interview.drone.backend.entity.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Integer> {
    List<DeliveryDetails> findByDeliveryDeliveryId(long deliveryId);
}

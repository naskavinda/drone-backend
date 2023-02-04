package com.interview.drone.backend.repository;

import com.interview.drone.backend.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findByCodeIn(List<String> ids);
}

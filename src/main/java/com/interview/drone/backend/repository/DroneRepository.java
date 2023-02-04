package com.interview.drone.backend.repository;

import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.entity.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findByDroneStateIn(List<DroneState> loadedDroneStates);
}

package com.interview.drone.backend.repository;

import com.interview.drone.backend.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface DroneRepository extends JpaRepository<Drone, BigInteger> {
}

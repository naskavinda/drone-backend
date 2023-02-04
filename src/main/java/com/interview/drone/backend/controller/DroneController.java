package com.interview.drone.backend.controller;

import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/drone", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping()
    private ResponseEntity<Drone> registerDrone(@Valid @RequestBody Drone drone) {
        Drone droneResult = droneService.registerDrone(drone);
        return ResponseEntity.ok(droneResult);
    }

    @PostMapping("/load")
    private ResponseEntity<Map<String, String>> loadMedication(@Valid @RequestBody LoadDroneDTO loadDrone) {
        droneService.loadMedicationToDrone(loadDrone);
        return ResponseEntity.ok(Map.of());
    }
}

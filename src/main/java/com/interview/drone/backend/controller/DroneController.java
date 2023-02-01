package com.interview.drone.backend.controller;

import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.service.DroneService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/drone", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping()
    private ResponseEntity<String> registerDrone(@RequestBody Drone drone) {
        String serialNumber = droneService.registerDrone(drone);
        return ResponseEntity.ok(String.format("Drone Serial Number is %s", serialNumber));
    }
}

package com.interview.drone.backend.controller;

import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.LoadDroneRequest;
import com.interview.drone.backend.dto.LoadedMedicationResponse;
import com.interview.drone.backend.dto.RegisterDroneRequest;
import com.interview.drone.backend.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/drone")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping()
    ResponseEntity<DroneResponse> registerDrone(@Validated @RequestBody RegisterDroneRequest drone) {
        try {
            DroneResponse droneResult = droneService.registerDrone(drone);
            return ResponseEntity.status(HttpStatus.CREATED).body(droneResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/medications")
    ResponseEntity<Map<String, String>> loadMedication(@Valid @RequestBody LoadDroneRequest loadDrone) {
        try {
            droneService.loadMedicationToDrone(loadDrone);
            return ResponseEntity.ok(Map.of("message", "Medication loaded successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Loading medication failed!"));
        }
    }

    @GetMapping("/{serialNumber}/medications")
    ResponseEntity<List<LoadedMedicationResponse>> getMedicationByDrone(@PathVariable String serialNumber) {
        try {
            List<LoadedMedicationResponse> loadedMedicationResponses = droneService.getMedicationByDrone(serialNumber);
            return ResponseEntity.ok(loadedMedicationResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/available")
    ResponseEntity<List<DroneResponse>> getAvailableDrones() {
        try {
            List<DroneResponse> droneResponseList = droneService.getAvailableDrones();
            return ResponseEntity.ok(droneResponseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{serialNumber}/batteryLevel")
    ResponseEntity<Map<String, Double>> getBatteryLevelByDrone(@PathVariable String serialNumber) {
        try {
            double batteryLevel = droneService.getBatteryLevelByDrone(serialNumber);
            return ResponseEntity.ok(Map.of("batteryLevel", batteryLevel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

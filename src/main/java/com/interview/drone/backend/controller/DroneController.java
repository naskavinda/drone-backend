package com.interview.drone.backend.controller;

import com.interview.drone.backend.dto.DroneResponse;
import com.interview.drone.backend.dto.LoadDroneDTO;
import com.interview.drone.backend.dto.LoadedMedicationResponse;
import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<Drone> registerDrone(@Valid @RequestBody Drone drone) {
        Drone droneResult = droneService.registerDrone(drone);
        return ResponseEntity.ok(droneResult);
    }

    @PostMapping("/medication")
    private ResponseEntity<Map<String, String>> loadMedication(@Valid @RequestBody LoadDroneDTO loadDrone) {
        droneService.loadMedicationToDrone(loadDrone);
        return ResponseEntity.ok(Map.of());
    }

    @GetMapping("/{serialNumber}/medication")
    private ResponseEntity<List<LoadedMedicationResponse>> getMedicationByDrone(@PathVariable String serialNumber) {
        List<LoadedMedicationResponse> loadedMedicationResponses = droneService.getMedicationByDrone(serialNumber);
        return ResponseEntity.ok(loadedMedicationResponses);
    }

    @GetMapping("/available")
    private ResponseEntity<List<DroneResponse>> getAvailableDrones(){
        List<DroneResponse> droneResponseList = droneService.getAvailableDrones();
        return ResponseEntity.ok(droneResponseList);
    }

    @GetMapping("/{serialNumber}/batteryLevel")
    private ResponseEntity<Map<String, Double>> getBatteryLevelByDrone(@PathVariable String serialNumber){
        double batteryLevel = droneService.getBatteryLevelByDrone(serialNumber);
        return ResponseEntity.ok(Map.of("batteryLevel", batteryLevel));
    }
}

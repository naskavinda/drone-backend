package com.interview.drone.backend.scheduler;

import com.interview.drone.backend.entity.Drone;
import com.interview.drone.backend.repository.DroneRepository;
import com.interview.drone.backend.service.BatteryLevelService;
import com.interview.drone.backend.service.impl.InMemoryBatteryLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatteryLevelTaskScheduler {

    private static final Logger auditLogger = LoggerFactory.getLogger("audit");
    private static final Logger LOGGER = LoggerFactory.getLogger(BatteryLevelTaskScheduler.class);

    @Qualifier(value = "BatteryLevelInMemoryService")
    private final BatteryLevelService batteryLevelService;
    private final DroneRepository droneRepository;

    public BatteryLevelTaskScheduler(InMemoryBatteryLevelService batteryLevelService, DroneRepository droneRepository) {
        this.batteryLevelService = batteryLevelService;
        this.droneRepository = droneRepository;
    }

    @Scheduled(fixedRate = 20000)
    public void checkBatteryLevels() {
        LOGGER.info("Start Scheduler..........................");
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones) {
            int droneBatteryLevel = batteryLevelService.getDroneBatteryLevel(drone.getSerialNumber(), drone.getDroneState().toString());
            auditLogger.info(String.format("Drone %s battery level Previous %d battery level Current %d drone status %s", drone.getSerialNumber(), drone.getBatteryCapacity(), droneBatteryLevel, drone.getDroneState().toString()));
            drone.setBatteryCapacity(droneBatteryLevel);
        }
        droneRepository.saveAll(drones);
        LOGGER.info("End Scheduler..........................");
    }
}

package com.interview.drone.backend.service.impl;

import com.interview.drone.backend.service.BatteryLevelService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class BatteryLevelInMemoryService implements BatteryLevelService {

    private final Map<String, Integer> batteryLevels = new HashMap<>();
    private final Random random = new Random();

    public int getDroneBatteryLevel(String droneId, String state) {
        int previousBatteryLevel = getPreviousBatteryLevel(droneId);
        int currentBatteryLevel = previousBatteryLevel;

        if (state.equals("IDLE")) {
            currentBatteryLevel = previousBatteryLevel + random.nextInt(100 - previousBatteryLevel + 1);
        } else {
            currentBatteryLevel = previousBatteryLevel - random.nextInt(previousBatteryLevel - (previousBatteryLevel * 92) / 100);
        }

        updateBatteryLevel(droneId, currentBatteryLevel);

        return currentBatteryLevel;
    }

    private int getPreviousBatteryLevel(String droneId) {
        return batteryLevels.getOrDefault(droneId, 100);
    }

    private void updateBatteryLevel(String droneId, int batteryLevel) {
        batteryLevels.put(droneId, batteryLevel);
    }
}

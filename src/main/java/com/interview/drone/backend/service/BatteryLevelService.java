package com.interview.drone.backend.service;

public interface BatteryLevelService {

    int getDroneBatteryLevel(String droneId, String state);
}

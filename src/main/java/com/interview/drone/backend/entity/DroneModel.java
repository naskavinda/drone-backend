package com.interview.drone.backend.entity;

public enum DroneModel {
    LIGHT_WEIGHT(1),
    MIDDLE_WEIGHT(2),
    CRUISER_WEIGHT(3),
    HEAVY_WEIGHT(4);

    private final Integer id;

    DroneModel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static DroneModel valueOf(Integer id) {
        for (DroneModel value : values()) {
            if (value.getId().equals(id)) {
                return value;
            }
        }
        return null;
    }
}

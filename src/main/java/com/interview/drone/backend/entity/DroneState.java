package com.interview.drone.backend.entity;

public enum DroneState {
    IDLE(1),
    LOADING(2),
    LOADED(3),
    DELIVERING(4),
    DELIVERED(5),
    RETURNING(6);

    private final int id;

    DroneState(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static DroneState valueOf(Integer id) {
        for (DroneState value : values()) {
            if (value.getId().equals(id)) {
                return value;
            }
        }
        return null;
    }
}

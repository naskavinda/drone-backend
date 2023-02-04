package com.interview.drone.backend.service.impl.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoadDroneValidatorChainOrder {

    public static final int DRONE_SERIAL_NUMBER_VALIDATOR_ORDER = 1;
    public static final int MEDICATION_CODE_LIST_VALIDATOR_ORDER = 2;
    public static final int ADDRESS_ID_VALIDATOR_ORDER = 3;
    public static final int BATTERY_LEVEL_VALIDATOR_ORDER = 4;
    public static final int WEIGHT_LIMIT_VALIDATOR_ORDER = 5;
    public static final int ALLOWED_DRONE_STATUS_VALIDATOR_ORDER = 6;

}

package com.interview.drone.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedValueValidator implements ConstraintValidator<AllowedConstraint, String> {

    private String[] allowedValues;

    @Override
    public void initialize(AllowedConstraint constraintAnnotation) {
        this.allowedValues = constraintAnnotation.allowedValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.equals("")) {
            return false;
        }
        for (String values : allowedValues) {
            if (value.equals(values)) {
                return true;
            }
        }
        return false;
    }
}

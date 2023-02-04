package com.interview.drone.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedValueValidator.class)
public @interface AllowedConstraint {

    String[] allowedValues();

    String message() default "must be a Allowed Value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

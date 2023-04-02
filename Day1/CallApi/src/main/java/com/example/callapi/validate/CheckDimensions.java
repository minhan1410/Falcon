package com.example.callapi.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckDimensionsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDimensions {
    String message() default "dimensions in date, app_bundle_id, country, platform";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

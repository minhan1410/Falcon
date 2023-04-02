package com.example.callapi.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CheckDimensionsValidator implements ConstraintValidator<CheckDimensions, List<String>> {
    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        if (strings.isEmpty()) {
            return false;
        }

        return List.of("date",
                "app_bundle_id",
                "country",
                "platform").containsAll(strings);
    }
}

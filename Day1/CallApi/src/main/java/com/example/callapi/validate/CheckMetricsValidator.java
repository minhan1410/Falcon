package com.example.callapi.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CheckMetricsValidator implements ConstraintValidator<CheckMetrics, List<String>> {
    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        if (strings.isEmpty()) {
            return false;
        }

        return List.of("revenue",
                "impression",
                "eCPM").containsAll(strings);
    }
}

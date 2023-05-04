package com.tahademiryol.commonpackage.utils.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotFutureYearValidator implements ConstraintValidator<NotFutureYear, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        var currentYear = java.time.Year.now().getValue();
        return value <= currentYear;
    }
}

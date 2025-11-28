package com.miguel.jobnest.infrastructure.configurations.annotations.validators;

import com.miguel.jobnest.infrastructure.configurations.annotations.EnumCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumCheck, String> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumCheck constraintAnnotation) {
        this.enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(this.enumValues).map(Enum::name).anyMatch(enumValue -> enumValue.equals(value));
    }
}
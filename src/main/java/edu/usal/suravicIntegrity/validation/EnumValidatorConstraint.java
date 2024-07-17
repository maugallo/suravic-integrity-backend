package edu.usal.suravicIntegrity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {

    Set<String> values;
    String enumValues;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
        enumValues = String.join(", ", values);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!values.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("El valor ingresado debe pertenecer a uno de los siguientes valores: %s", enumValues))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
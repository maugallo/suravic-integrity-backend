package edu.usal.suravicIntegrity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, Object> {

    Set<String> values;
    String enumValues;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        // Recopilamos los valores del enum en un Set
        values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
        // Guardamos los valores como una cadena separada por comas para el mensaje de error
        enumValues = String.join(", ", values);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // Si el valor es null, es válido (en combinación con @NotNull evitará nulls)
        if (value == null) {
            return true;
        }

        // Verificamos si el valor es un String
        if (value instanceof String) {
            return validateSingleValue((String) value, context);
        }

        // Verificamos si el valor es un Set
        if (value instanceof Set) {
            return validateSetValues((Set<?>) value, context);
        }

        // Si no es ni String ni Set, devolvemos falso (no válido)
        return false;
    }

    private boolean validateSingleValue(String value, ConstraintValidatorContext context) {
        if (!values.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("El valor ingresado debe pertenecer a uno de los siguientes valores: %s", enumValues))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateSetValues(Set<?> set, ConstraintValidatorContext context) {
        // Verificamos que todos los elementos del Set sean Strings y que estén en el Enum
        for (Object obj : set) {
            if (!(obj instanceof String) || !values.contains(obj)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                String.format("Todos los valores en el conjunto deben pertenecer a: %s", enumValues))
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}

package edu.usal.suravicIntegrity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueValidator {

    String message() default "El valor debe ser único";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entityClass(); // Clase de la entidad a validar.
    String fieldName(); // Nombre del campo a validar de la entidad.
}

package edu.usal.suravicIntegrity.validation;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidatorConstraint implements ConstraintValidator<UniqueValidator, Object> {

    @Autowired
    private EntityManager entityManager;

    Class<?> entityClass;
    String fieldName;

    @Override
    public void initialize(UniqueValidator constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || (value instanceof String && ((String) value).isEmpty())) {
            return true;
        }

        String query = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                entityClass.getSimpleName(), fieldName);

        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0; // Retorna true si no se encuentra ninguna coincidencia
    }

}

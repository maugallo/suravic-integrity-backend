package edu.usal.suravicIntegrity.contact;

import edu.usal.suravicIntegrity.validation.UniqueValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDTO {

    @NotBlank
    @Size(min = 7, max = 12)
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo acepta números")
    @UniqueValidator(entityClass = Contact.class, fieldName = "telephone", message = "El teléfono ingresado ya existe")
    private String telephone;

    @Size(max = 50)
    @Pattern(regexp = "(^$|^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$)", message = "El email no cumple el formato necesario")
    @UniqueValidator(entityClass = Contact.class, fieldName = "email", message = "El email ingresado ya existe")
    private String email;

}

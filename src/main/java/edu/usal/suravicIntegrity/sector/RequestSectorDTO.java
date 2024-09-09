package edu.usal.suravicIntegrity.sector;

import edu.usal.suravicIntegrity.validation.UniqueValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSectorDTO {

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$", message = "El rubro solo acepta letras")
    @UniqueValidator(entityClass = Sector.class, fieldName = "name", message = "El rubro ingresado ya existe")
    private String name;

}

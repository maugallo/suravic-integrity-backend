package edu.usal.suravicIntegrity.category;

import edu.usal.suravicIntegrity.sector.Sector;
import edu.usal.suravicIntegrity.validation.UniqueValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$", message = "La categoría solo acepta letras")
    @UniqueValidator(entityClass = Sector.class, fieldName = "name", message = "La categoría ingresada ya existe")
    private String name;

}

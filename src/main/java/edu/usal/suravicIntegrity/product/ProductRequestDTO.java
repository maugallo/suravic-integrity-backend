package edu.usal.suravicIntegrity.product;

import edu.usal.suravicIntegrity.category.CategoryResponseDTO;
import edu.usal.suravicIntegrity.provider.ProviderResponseDTO;
import edu.usal.suravicIntegrity.user.UserResponseDTO;
import edu.usal.suravicIntegrity.validation.UniqueValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequestDTO {

    private Long categoryId;

    private Long providerId;

    private Long userId;

    @Size(max = 10)
    @UniqueValidator(entityClass = Product.class, fieldName = "plu", message = "El plu ingresado ya existe")
    private String plu;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]+$", message = "El título no puede tener caracteres especiales")
    private String title;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999999)
    private Double price;

}

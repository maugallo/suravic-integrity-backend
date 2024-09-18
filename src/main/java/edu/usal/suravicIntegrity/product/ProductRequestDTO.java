package edu.usal.suravicIntegrity.product;

import edu.usal.suravicIntegrity.category.Category;
import edu.usal.suravicIntegrity.category.CategoryResponseDTO;
import edu.usal.suravicIntegrity.provider.Provider;
import edu.usal.suravicIntegrity.provider.ProviderResponseDTO;
import edu.usal.suravicIntegrity.user.User;
import edu.usal.suravicIntegrity.user.UserResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    private CategoryResponseDTO category;

    private ProviderResponseDTO provider;

    private UserResponseDTO user;

    @Size(min = 2, max = 10)
    private String plu;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Min(value = 1)
    @Max(value = 9999999)
    private Double price;

}

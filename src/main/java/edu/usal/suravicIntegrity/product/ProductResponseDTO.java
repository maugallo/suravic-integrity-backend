package edu.usal.suravicIntegrity.product;

import edu.usal.suravicIntegrity.category.CategoryResponseDTO;
import edu.usal.suravicIntegrity.provider.ProviderResponseDTO;
import edu.usal.suravicIntegrity.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;

    private CategoryResponseDTO category;

    private ProviderResponseDTO provider;

    private UserResponseDTO user;

    private String plu;

    private String title;

    private Double price;

    private Boolean isEnabled;

}

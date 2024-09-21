package edu.usal.suravicIntegrity.product;

import edu.usal.suravicIntegrity.category.CategoryMapper;
import edu.usal.suravicIntegrity.provider.ProviderMapper;
import edu.usal.suravicIntegrity.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    default ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());

        dto.setCategory(CategoryMapper.INSTANCE.toDTO(product.getCategory()));
        dto.setProvider(ProviderMapper.INSTANCE.toDTO(product.getProvider()));
        dto.setUser(UserMapper.INSTANCE.toDTO(product.getUser()));

        dto.setPlu(product.getPlu());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());

        return dto;
    }

    default Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setPlu(dto.getPlu());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());

        return product;
    }

}

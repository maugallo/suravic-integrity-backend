package edu.usal.suravicIntegrity.meatDetails;

import edu.usal.suravicIntegrity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeatDetailsMapper {

    MeatDetailsMapper INSTANCE = Mappers.getMapper(MeatDetailsMapper.class);

    default ProductWithMeatDetailsDTO toDTO(MeatDetails meatDetails) {
        ProductWithMeatDetailsDTO dto = new ProductWithMeatDetailsDTO();
        Product product = meatDetails.getProduct();

        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setPercentage(meatDetails.getPercentage());
        dto.setWeight(meatDetails.getWeight());

        return dto;
    }

    default MeatDetails toEntity(ProductWithMeatDetailsDTO dto) {
        MeatDetails meatDetails = new MeatDetails();

        meatDetails.setId(dto.getId()); // Mantiene el mismo ID que su product.
        meatDetails.setPercentage(dto.getPercentage());
        meatDetails.setWeight(dto.getWeight());

        return meatDetails;
    }

}

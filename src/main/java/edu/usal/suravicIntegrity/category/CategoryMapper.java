package edu.usal.suravicIntegrity.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    default CategoryResponseDTO toDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIsEnabled(category.getIsEnabled());

        return dto;
    }

    default Category toEntityFromRequest(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());

        return category;
    }

    default Category toEntityFromResponse(CategoryResponseDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return category;
    }

}

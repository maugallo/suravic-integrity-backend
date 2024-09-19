package edu.usal.suravicIntegrity.category;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // GET METHODS:
    public List<CategoryResponseDTO> findAll(Boolean isEnabled) {
        List<Category> categories = categoryRepository.findByIsEnabled(isEnabled);

        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar la categoría solicitada con id " + id));
    }

    public CategoryResponseDTO findCategoryResponseById(Long id) {
        Category category = this.findCategoryById(id);

        return categoryMapper.toDTO(category);
    }

    // CREATE METHOD:
    public String addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntityFromRequest(categoryRequestDTO);
        category.setIsEnabled(true);
        categoryRepository.save(category);

        return "Categoría creada correctamente";
    }

    // PUT METHOD:
    public String updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = this.findCategoryById(id);

        category.setName(categoryRequestDTO.getName());
        categoryRepository.save(category);

        return "Categoría actualizada correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        Category category = this.findCategoryById(id);

        category.setIsEnabled(!category.getIsEnabled());
        categoryRepository.save(category);

        return (category.getIsEnabled() ? "Categoría recuperada correctamente" : "Categoría eliminada correctamente");
    }

}

package edu.usal.suravicIntegrity.product;

import edu.usal.suravicIntegrity.category.Category;
import edu.usal.suravicIntegrity.category.CategoryService;
import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import edu.usal.suravicIntegrity.provider.Provider;
import edu.usal.suravicIntegrity.provider.ProviderService;
import edu.usal.suravicIntegrity.user.User;
import edu.usal.suravicIntegrity.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final CategoryService categoryService;
    private final ProviderService providerService;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ProductService(CategoryService categoryService, ProviderService providerService, UserService userService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.providerService = providerService;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    // GET METHODS:
    public List<ProductResponseDTO> findProducts(Boolean isEnabled) {
        List<Product> products = productRepository.findByIsEnabled(isEnabled);

        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el producto solicitado con id " + id));
    }

    public ProductResponseDTO findProductResponseById(Long id) {
        Product product = this.findProductById(id);

        return productMapper.toDTO((product));
    }

    // CREATE METHOD:
    @Transactional
    public String addProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);

        product.setIsEnabled(true);
        product.setCategory(categoryService.findCategoryById(productRequestDTO.getCategoryId()));
        product.setProvider(providerService.findProviderById(productRequestDTO.getProviderId()));
        product.setUser(userService.findUserById(productRequestDTO.getUserId()));

        productRepository.save(product);

        return "Producto agregado correctamente";
    }

    // PUT METHOD:
    @Transactional
    public String updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = this.findProductById(id);

        Category updatedCategory = categoryService.findCategoryById(productRequestDTO.getCategoryId());
        Provider updatedProvider = providerService.findProviderById(productRequestDTO.getProviderId());
        User updatedUser = userService.findUserById(productRequestDTO.getUserId());

        product.setCategory(updatedCategory);
        product.setProvider(updatedProvider);
        product.setUser(updatedUser);
        product.setPlu(productRequestDTO.getPlu());
        product.setTitle(productRequestDTO.getTitle());
        product.setPrice(productRequestDTO.getPrice());
        productRepository.save(product);

        return "Producto actualizado correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        Product product = this.findProductById(id);

        product.setIsEnabled(!product.getIsEnabled());
        productRepository.save(product);

        return (product.getIsEnabled()) ? "Producto recuperado correctamente" : "Producto eliminado correctamente";
    }

}

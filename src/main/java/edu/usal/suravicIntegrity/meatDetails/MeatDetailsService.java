package edu.usal.suravicIntegrity.meatDetails;

import edu.usal.suravicIntegrity.product.Product;
import edu.usal.suravicIntegrity.product.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MeatDetailsService {

    private final MeatDetailsRepository meatDetailsRepository;
    private final ProductRepository productRepository;

    public MeatDetailsService(MeatDetailsRepository meatDetailsRepository, ProductRepository productRepository) {
        this.meatDetailsRepository = meatDetailsRepository;
        this.productRepository = productRepository;
    }

    // GET METHODS:
    public List<MeatDetailsDTO> findMeatDetailsDTO(String category) throws IllegalArgumentException {
        List<MeatDetails> meatDetails = switch (category) {
            case "Carnes" -> meatDetailsRepository.findAllByIdBetween(1L, 29L);
            case "Pollos" -> meatDetailsRepository.findAllByIdBetween(30L, 35L);
            default -> throw new IllegalArgumentException("La categoría ingresada para buscar detalles de carne no es válida");
        };

        return meatDetails.stream()
                .map(MeatDetailsMapper.INSTANCE::toDTO)
                .toList();
    }

    // CREATE METHOD:
    public String addMeatDetails(MeatDetails meatDetails) {
        meatDetailsRepository.save(meatDetails);

        return "Detalles de carne agregados correctamente";
    }

    // PUT METHOD:
    @Transactional
    public String updateProductsWithMeatDetails(List<MeatDetailsDTO> productsWithMeatDetails) {
        // Products y MeatDetails comparten mismo ID.
        List<Long> productsIds = productsWithMeatDetails.stream()
                .map(MeatDetailsDTO::getId)
                .toList();

        // Primero actualizo los precios de cada producto.
        updateProductsPrice(productsIds, productsWithMeatDetails);

        // Luego actualizo el weight de cada meat detail.
        updateMeatDetails(productsIds, productsWithMeatDetails);

        return "Detalles de carne actualizados correctamente";
    }

    private void updateProductsPrice(List<Long> productsIds, List<MeatDetailsDTO> productsWithMeatDetails) {
        Map<Long, Product> productsMap = productRepository.findAllById(productsIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        productsWithMeatDetails.forEach(productWithMeatDetails -> {
            Product product = productsMap.get(productWithMeatDetails.getId());
            product.setPrice(productWithMeatDetails.getPrice());
        });

        productRepository.saveAll(productsMap.values());
    }

    private void updateMeatDetails(List<Long> productsIds, List<MeatDetailsDTO> productsWithMeatDetails) {
        Map<Long, MeatDetails> meatDetailsMap = meatDetailsRepository.findAllById(productsIds).stream()
                .collect(Collectors.toMap(MeatDetails::getId, meatDetail -> meatDetail));

        productsWithMeatDetails.forEach(productWithMeatDetails-> {
            MeatDetails meatDetail = meatDetailsMap.get(productWithMeatDetails.getId());
            meatDetail.setWeight(productWithMeatDetails.getWeight());
        });

        meatDetailsRepository.saveAll(meatDetailsMap.values());
    }

}

package edu.usal.suravicIntegrity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.usal.suravicIntegrity.category.Category;
import edu.usal.suravicIntegrity.category.CategoryRepository;
import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactRepository;
import edu.usal.suravicIntegrity.meatDetails.*;
import edu.usal.suravicIntegrity.percentages.Percentages;
import edu.usal.suravicIntegrity.percentages.PercentagesRepository;
import edu.usal.suravicIntegrity.product.Product;
import edu.usal.suravicIntegrity.product.ProductRepository;
import edu.usal.suravicIntegrity.provider.Provider;
import edu.usal.suravicIntegrity.provider.ProviderRepository;
import edu.usal.suravicIntegrity.provider.VatCondition;
import edu.usal.suravicIntegrity.sector.Sector;
import edu.usal.suravicIntegrity.sector.SectorRepository;
import edu.usal.suravicIntegrity.user.User;
import edu.usal.suravicIntegrity.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MeatDetailsRepository meatDetailsRepository;
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private final ObjectMapper objectMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SectorRepository sectorRepository;
    private final ProviderRepository providerRepository;
    private final ContactRepository contactRepository;
    private final PercentagesRepository percentagesRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           SectorRepository sectorRepository, ProviderRepository providerRepository,
                           CategoryRepository categoryRepository, ProductRepository productRepository,
                           ContactRepository contactRepository, PercentagesRepository percentagesRepository,
                           ObjectMapper objectMapper, MeatDetailsRepository meatDetailsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sectorRepository = sectorRepository;
        this.providerRepository = providerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.contactRepository = contactRepository;
        this.percentagesRepository = percentagesRepository;
        this.objectMapper = objectMapper;
        this.meatDetailsRepository = meatDetailsRepository;
    }

    @Override
    public void run(String... args) {
        User adminUser = initializeAdminUser();
        if (sectorRepository.findByName("Carnes").isEmpty()) initializeBeef(adminUser);
        if(sectorRepository.findByName("Pollos").isEmpty()) initializeChicken(adminUser);

        System.out.println("Data initialization completed!");
    }

    private User initializeAdminUser() {
        return userRepository.findByUsername(adminUsername).orElseGet(() -> {
            User adminUser = new User();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole("DUENO");
            adminUser.setIsEnabled(true);
            return userRepository.save(adminUser);
        });
    }

    private void initializeBeef(User adminUser) {
        Provider beefProvider = initializeBeefProvider();
        initializeBeefProducts(beefProvider, adminUser);
    }

    private Provider initializeBeefProvider() {
        Sector sector = sectorRepository.save(new Sector(null, "Carnes", null, null, true));

        Provider provider = new Provider();
        provider.setContact(contactRepository.save(new Contact(null, "juanpampeano@gmail.com", "12345678", null, null)));
        provider.setPercentages(percentagesRepository.save(new Percentages(null, 50.0, 15.5, null, null)));
        provider.setSector(sector);
        provider.setCompanyName("Novillo Pampeano");
        provider.setFirstName("Juan");
        provider.setLastName("Pérez");
        provider.setCuit("20-12345678-9");
        provider.setVatCondition(VatCondition.IVA_RESPONSABLE_INSCRIPTO.name());
        provider.setIsEnabled(true);
        return providerRepository.save(provider);
    }

    private void initializeBeefProducts(Provider provider, User adminUser) {
        Category category = categoryRepository.save(new Category(null, "Carnes", null, null, true));

        List<MeatDetailsDTO> beefProducts = loadProductsFromJson("beef-products.json");
        beefProducts.forEach(beefProduct -> {
            Product product = productRepository.save(new Product(null, category, provider, adminUser, beefProduct.getPlu(), beefProduct.getTitle(), beefProduct.getPrice(), null, null, true));
            meatDetailsRepository.save(new MeatDetails(null, product, beefProduct.getPercentage(), beefProduct.getWeight()));
        });
    }

    private void initializeChicken(User adminUser) {
        Provider chickenProvider = initializeChickenProvider();
        initializeChickenProducts(chickenProvider, adminUser);
    }

    private Provider initializeChickenProvider(){
        Sector sector = sectorRepository.save(new Sector(null, "Pollos", null, null, true));

        Provider provider = new Provider();
        provider.setContact(contactRepository.save(new Contact(null, "12345678", "suravicjorge@gmail.com", null, null)));
        provider.setPercentages(percentagesRepository.save(new Percentages(null, 30.0, 15.5, null, null)));
        provider.setSector(sector);
        provider.setCompanyName("Suravic");
        provider.setFirstName("Jorge");
        provider.setLastName("Hernandez");
        provider.setCuit("20-11235678-9");
        provider.setVatCondition(VatCondition.IVA_RESPONSABLE_INSCRIPTO.name());
        provider.setIsEnabled(true);
        return providerRepository.save(provider);
    }

    private void initializeChickenProducts(Provider provider, User adminUser) {
        Category category = categoryRepository.save(new Category(null, "Pollos", null, null, true));

        List<MeatDetailsDTO> beefProducts = loadProductsFromJson("chicken-products.json");
        beefProducts.forEach(beefProduct -> {
            Product product = productRepository.save(new Product(null, category, provider, adminUser, beefProduct.getPlu(), beefProduct.getTitle(), beefProduct.getPrice(), null, null, true));
            meatDetailsRepository.save(new MeatDetails(null, product, beefProduct.getPercentage(), beefProduct.getWeight()));
        });
    }

    private List<MeatDetailsDTO> loadProductsFromJson(String fileName) {
        try {
            File file = new File("src/main/resources/data/" + fileName);
            return Arrays.asList(objectMapper.readValue(file, MeatDetailsDTO[].class));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading products from JSON: " + fileName);
        }
    }
}
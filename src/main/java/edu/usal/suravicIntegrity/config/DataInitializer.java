package edu.usal.suravicIntegrity.config;

import edu.usal.suravicIntegrity.category.Category;
import edu.usal.suravicIntegrity.category.CategoryRepository;
import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactRepository;
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

@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

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
                           CategoryRepository categoryRepository, ProductRepository productRepository, ContactRepository contactRepository, PercentagesRepository percentagesRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sectorRepository = sectorRepository;
        this.providerRepository = providerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.contactRepository = contactRepository;
        this.percentagesRepository = percentagesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Inicializar el usuario admin
        User adminUser = initializeAdminUser();

        // Inicializar datos dependientes del usuario admin
        initializeData(adminUser);

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

    private void initializeData(User adminUser) {
        // Verificar y crear sector "Carnes"
        Sector sector = sectorRepository.findByName("Carnes").orElseGet(() -> {
            Sector newSector = new Sector();
            newSector.setName("Carnes");
            newSector.setIsEnabled(true);
            return sectorRepository.save(newSector);
        });

        // Verificar y crear categoría "Carnes"
        Category category = categoryRepository.findByName("Carnes").orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName("Carnes");
            newCategory.setIsEnabled(true);
            return categoryRepository.save(newCategory);
        });

        // Verificar y crear proveedor mock
        Provider provider = providerRepository.findByCompanyName("Proveedor Mock").orElseGet(() -> {
            Contact newContact = new Contact();
            newContact.setEmail("");
            newContact.setTelephone("12345678");

            Percentages newPercentages = new Percentages();
            newPercentages.setVatPercentage(50.0);
            newPercentages.setProfitPercentage(15.5);

            Provider newProvider = new Provider();
            newProvider.setContact(contactRepository.save(newContact));
            newProvider.setPercentages(percentagesRepository.save(newPercentages));
            newProvider.setCompanyName("Proveedor Mock");
            newProvider.setFirstName("Juan");
            newProvider.setLastName("Pérez");
            newProvider.setCuit("20-12345678-9");
            newProvider.setVatCondition(VatCondition.IVA_RESPONSABLE_INSCRIPTO.name());
            newProvider.setIsEnabled(true);
            newProvider.setSector(sector);
            return providerRepository.save(newProvider);
        });

        // Verificar y crear producto mock
        productRepository.findByTitle("Producto de Carne").orElseGet(() -> {
            Product newProduct = new Product();
            newProduct.setTitle("Producto de Carne");
            newProduct.setPrice(1500.00);
            newProduct.setIsEnabled(true);
            newProduct.setCategory(category);
            newProduct.setProvider(provider);
            newProduct.setUser(adminUser);  // Asociar con el usuario admin
            return productRepository.save(newProduct);
        });
    }
}
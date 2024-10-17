package edu.usal.suravicIntegrity.config;

import edu.usal.suravicIntegrity.category.Category;
import edu.usal.suravicIntegrity.category.CategoryRepository;
import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactRepository;
import edu.usal.suravicIntegrity.meatDetails.MeatDetails;
import edu.usal.suravicIntegrity.meatDetails.MeatDetailsService;
import edu.usal.suravicIntegrity.percentages.Percentages;
import edu.usal.suravicIntegrity.percentages.PercentagesRepository;
import edu.usal.suravicIntegrity.product.ProductRepository;
import edu.usal.suravicIntegrity.product.ProductRequestDTO;
import edu.usal.suravicIntegrity.product.ProductService;
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
    private final ProductService productService;
    private final MeatDetailsService meatDetailsService;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           SectorRepository sectorRepository, ProviderRepository providerRepository,
                           CategoryRepository categoryRepository, ProductRepository productRepository, ContactRepository contactRepository, PercentagesRepository percentagesRepository, ProductService productService, MeatDetailsService meatDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sectorRepository = sectorRepository;
        this.providerRepository = providerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.contactRepository = contactRepository;
        this.percentagesRepository = percentagesRepository;
        this.productService = productService;
        this.meatDetailsService = meatDetailsService;
    }

    @Override
    public void run(String... args) throws Exception {
        User adminUser = initializeAdminUser();
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

        // Verificar y crear proveedor de carnes
        Provider provider = providerRepository.findByCompanyName("Novillo Pampeano").orElseGet(() -> {
            Contact newContact = new Contact();
            newContact.setEmail("juanpampeano@gmail.com");
            newContact.setTelephone("12345678");

            Percentages newPercentages = new Percentages();
            newPercentages.setVatPercentage(50.0);
            newPercentages.setProfitPercentage(15.5);

            Provider newProvider = new Provider();
            newProvider.setContact(contactRepository.save(newContact));
            newProvider.setPercentages(percentagesRepository.save(newPercentages));
            newProvider.setCompanyName("Novillo Pampeano");
            newProvider.setFirstName("Juan");
            newProvider.setLastName("Pérez");
            newProvider.setCuit("20-12345678-9");
            newProvider.setVatCondition(VatCondition.IVA_RESPONSABLE_INSCRIPTO.name());
            newProvider.setIsEnabled(true);
            newProvider.setSector(sector);
            return providerRepository.save(newProvider);
        });

        // Crear productos de carne:
        if (productRepository.findByTitle("Asado").isEmpty()) {
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Asado", 5900.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Asado"), 8.83, 9.8));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Asado americano", 6980.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Asado americano"), 2.16, 2.4));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Bife ancho", 6390.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Bife ancho"), 3.15, 3.5));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Bife angosto", 6490.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Bife angosto"), 4.14, 4.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Bola de lomo", 6200.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Bola de lomo"), 3.24, 3.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Carne picada comun", 5250.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Carne picada comun"), 4.23, 4.7));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Carne picada especial", 5500.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Carne picada especial"), 1.35, 1.5));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Chicicuela", 3060.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Chicicuela"), 0.45, 0.5));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Colita de cuadril", 8130.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Colita de cuadril"), 0.99, 1.1));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Cuadrada", 6270.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Cuadrada"), 3.24, 3.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Cuadril", 6470.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Cuadril"), 2.25, 2.5));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Entraña", 9440.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Entraña"), 0.54, 0.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Espinazo", 2400.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Espinazo"), 3.24, 3.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Falda parrillera", 4200.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Falda parrillera"), 1.44, 1.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Falda puchero", 3800.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Falda puchero"), 2.07, 2.3));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Grasa vacuna", 50.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Grasa vacuna"), 8.47, 9.4));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Lomo", 9270.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Lomo"), 1.44, 1.6));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Matambre", 7400.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Matambre"), 1.62, 1.8));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Nalga", 7245.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Nalga"), 3.96, 4.4));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Osobuco", 3500.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Osobuco"), 7.12, 7.9));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Paleta", 5600.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Paleta"), 4.95, 5.5));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Palomita", 5600.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Palomita"), 0.81, 0.9));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Peceto", 7850.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Peceto"), 1.53, 1.7));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Picaña", 5400.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Picaña"), 1.17, 1.3));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Roast beef", 5490.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Roast beef"), 4.86, 5.4));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Tapa de asado", 6470.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Tapa de asado"), 2.43, 2.7));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Tapa de nalga", 6470.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Tapa de nalga"), 1.53, 1.7));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Tortuguita", 5610.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Tortuguita"), 1.53, 1.7));

            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Vacío", 6950.0));
            meatDetailsService.addMeatDetails(new MeatDetails(null, productService.findProductByTitle("Vacío"), 4.32, 4.8));

        }
    }
}
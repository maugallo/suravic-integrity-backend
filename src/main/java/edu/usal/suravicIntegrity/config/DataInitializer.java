package edu.usal.suravicIntegrity.config;

import edu.usal.suravicIntegrity.category.Category;
import edu.usal.suravicIntegrity.category.CategoryRepository;
import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.contact.ContactRepository;
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

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           SectorRepository sectorRepository, ProviderRepository providerRepository,
                           CategoryRepository categoryRepository, ProductRepository productRepository, ContactRepository contactRepository, PercentagesRepository percentagesRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sectorRepository = sectorRepository;
        this.providerRepository = providerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.contactRepository = contactRepository;
        this.percentagesRepository = percentagesRepository;
        this.productService = productService;
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
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Asado", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Asado americado", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Bife ancho", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Bife angosto", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Bola de lomo", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Carne picada comun", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Carne picada especial", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Chicicuela", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Colita de cuadril", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Cuadrada", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Cuadril", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Entraña", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Espinazo", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Falda", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Falda Puchero", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Grasa vacuna", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Lomo", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Matambre", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Nalga", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Osobuco", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Paleta", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Palomita", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Peceto", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Picaña", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Roast beef", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Tapa de asado", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Tapa de nalga", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Tortuguita", 5400.0));
            productService.addProduct(new ProductRequestDTO(category.getId(), provider.getId(), adminUser.getId(), "", "Vacío", 5400.0));
        }
    }
}
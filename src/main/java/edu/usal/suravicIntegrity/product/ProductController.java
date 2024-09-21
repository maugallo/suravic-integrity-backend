package edu.usal.suravicIntegrity.product;

import edu.usal.suravicIntegrity.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ErrorHandler errorHandler;

    public ProductController(ProductService productService, ErrorHandler errorHandler) {
        this.productService = productService;
        this.errorHandler = errorHandler;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO')")
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getProducts(@RequestParam(required = true) Boolean isEnabled) {
        return new ResponseEntity<>(productService.findProducts(isEnabled), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DUENO')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductResponseById(id), HttpStatus.OK);
    }

    // CREATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PostMapping()
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productService.addProduct(productRequestDTO), HttpStatus.CREATED);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productService.updateProduct(id, productRequestDTO), HttpStatus.OK);
    }

    // DELETE & RECOVER METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id) {
        return new ResponseEntity<>(productService.toggleIsEnabled(id), HttpStatus.OK);
    }

}

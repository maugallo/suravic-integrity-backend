package edu.usal.suravicIntegrity.meatDetails;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meat-products")
public class MeatDetailsController {

    private final MeatDetailsService meatDetailsService;

    public MeatDetailsController(MeatDetailsService meatDetailsService) {
        this.meatDetailsService = meatDetailsService;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO')")
    @GetMapping()
    public ResponseEntity<List<ProductWithMeatDetailsDTO>> getProductsWithMeatDetails() {
        return new ResponseEntity<>(meatDetailsService.findMeatDetailsDTO(), HttpStatus.OK);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping()
    public ResponseEntity<String> updateProductsWithMeatDetails(@RequestBody List<ProductWithMeatDetailsDTO> productsWithMeatDetailsDTO) {
        return new ResponseEntity<>(meatDetailsService.updateProductsWithMeatDetails(productsWithMeatDetailsDTO), HttpStatus.OK);
    }

}

package edu.usal.suravicIntegrity.category;

import edu.usal.suravicIntegrity.ErrorHandler;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ErrorHandler errorHandler;

    public CategoryController(CategoryService categoryService, ErrorHandler errorHandler) {
        this.categoryService = categoryService;
        this.errorHandler = errorHandler;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO')")
    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(@RequestParam(required = true) Boolean isEnabled){
        return new ResponseEntity<>(categoryService.findAll(isEnabled), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DUENO')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findCategoryResponseById(id), HttpStatus.OK);
    }

    // CREATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PostMapping()
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categoryService.addCategory(categoryRequestDTO), HttpStatus.CREATED);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryRequestDTO), HttpStatus.OK);
    }

    // DELETE & RECOVER METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.toggleIsEnabled(id), HttpStatus.OK);
    }

}

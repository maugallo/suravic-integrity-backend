package edu.usal.suravicIntegrity.provider;

import edu.usal.suravicIntegrity.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private final ProviderService providerService;
    private final ErrorHandler errorHandler;

    public ProviderController(ProviderService providerService, ErrorHandler errorHandler) {
        this.providerService = providerService;
        this.errorHandler = errorHandler;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO')")
    @GetMapping()
    public ResponseEntity<List<ResponseProviderDTO>> getProviders(@RequestParam(required = true) Boolean isEnabled) {
        return new ResponseEntity<>(providerService.findProviders(isEnabled), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DUENO')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProviderDTO> getProvider(@PathVariable Long id) {
        return new ResponseEntity<>(providerService.findProviderById(id), HttpStatus.OK);
    }

    // CREATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PostMapping()
    public ResponseEntity<String> createProvider(@Valid @RequestBody RequestProviderDTO requestProviderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(providerService.addProvider(requestProviderDTO), HttpStatus.CREATED);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProvider(@PathVariable Long id, @RequestBody RequestProviderDTO requestProviderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(providerService.updateProvider(id, requestProviderDTO), HttpStatus.OK);
    }

    // DELETE & RECOVER METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id) {
        return new ResponseEntity<>(providerService.toggleIsEnabled(id), HttpStatus.OK);
    }

}

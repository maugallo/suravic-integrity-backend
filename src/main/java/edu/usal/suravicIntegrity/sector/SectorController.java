package edu.usal.suravicIntegrity.sector;

import edu.usal.suravicIntegrity.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    private final SectorService sectorService;
    private final ErrorHandler errorHandler;

    public SectorController(SectorService sectorService, ErrorHandler errorHandler) {
        this.sectorService = sectorService;
        this.errorHandler = errorHandler;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO')")
    @GetMapping()
    public ResponseEntity<List<SectorResponseDTO>> getSectors(@RequestParam(required = true) Boolean isEnabled){
        return new ResponseEntity<>(sectorService.findAll(isEnabled), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DUENO')")
    @GetMapping("/{id}")
    public ResponseEntity<SectorResponseDTO> getSectorById(@PathVariable Long id){
        return new ResponseEntity<>(sectorService.findById(id), HttpStatus.OK);
    }

    // CREATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PostMapping()
    public ResponseEntity<String> createSector(@Valid @RequestBody SectorRequestDTO sectorRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sectorService.addSector(sectorRequestDTO), HttpStatus.CREATED);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSector(@PathVariable Long id, @Valid @RequestBody SectorRequestDTO sectorRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sectorService.updateSector(id, sectorRequestDTO), HttpStatus.OK);
    }

    // DELETE & RECOVER METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id){
        return new ResponseEntity<>(sectorService.toggleIsEnabled(id), HttpStatus.OK);
    }

}

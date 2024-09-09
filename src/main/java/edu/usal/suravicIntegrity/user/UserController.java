package edu.usal.suravicIntegrity.user;

import edu.usal.suravicIntegrity.ErrorHandler;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ErrorHandler errorHandler;

    public UserController(UserService userService, ErrorHandler errorHandler) {
        this.userService = userService;
        this.errorHandler = errorHandler;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO') or hasRole('ENCARGADO')")
    @GetMapping()
    public ResponseEntity<List<ResponseUserDTO>> getUsers(@RequestParam(required = true) Boolean isEnabled){
        return new ResponseEntity<>(userService.findUsers(isEnabled), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DUENO') or hasRole('ENCARGADO')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    // CREATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PostMapping()
    public ResponseEntity<String> createUser(@Valid @RequestBody RequestUserDTO requestUserDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.addUser(requestUserDTO), HttpStatus.CREATED);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody RequestUserDTO requestUserDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.updateUser(id, requestUserDTO), HttpStatus.OK);
    }

    // DELETE & RECOVER METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id){
        return new ResponseEntity<>(userService.toggleIsEnabled(id), HttpStatus.OK);
    }

}

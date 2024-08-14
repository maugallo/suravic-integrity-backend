package edu.usal.suravicIntegrity.user;

import edu.usal.suravicIntegrity.ErrorHandler;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('DUENO')")
public class UserController {

    private final UserService userService;
    private final ErrorHandler errorHandler;

    @Autowired
    public UserController(UserService userService, ErrorHandler errorHandler) {
        this.userService = userService;
        this.errorHandler = errorHandler;
    }

    //GET METHODS:
    @GetMapping()
    public ResponseEntity<List<ResponseUserDTO>> getUsers(){
        return new ResponseEntity<List<ResponseUserDTO>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<ResponseUserDTO>(userService.findById(id), HttpStatus.OK);
    }

    //CREATE METHOD:
    @PostMapping()
    public ResponseEntity<String> createUser(@Valid @RequestBody RequestUserDTO requestUserDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.addUser(requestUserDTO), HttpStatus.CREATED);
    }

    //UPDATE METHOD:
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody RequestUserDTO requestUserDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.updateUser(id, requestUserDTO), HttpStatus.CREATED);
    }

    //DELETE & RECOVER METHOD:
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id){
        return new ResponseEntity<>(userService.toggleIsEnabled(id), HttpStatus.OK);
    }

}

package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.ErrorHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final ErrorHandler errorHandler;
    
    public OrderController(OrderService orderService, ErrorHandler errorHandler) {
        this.orderService = orderService;
        this.errorHandler = errorHandler;
    }

    // GET METHODS:
    @PreAuthorize("hasRole('DUENO')")
    @GetMapping()
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@RequestParam(required = true) Boolean isEnabled) {
        return new ResponseEntity<>(orderService.findOrders(isEnabled), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DUENO')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.findOrderResponseById(id), HttpStatus.OK);
    }

    // CREATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PostMapping()
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderService.addOrder(orderRequestDTO), HttpStatus.CREATED);
    }

    // UPDATE METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = errorHandler.loadErrorMessages(bindingResult);

            return new ResponseEntity<>(ErrorHandler.getErrorMessages(errorList), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderService.updateOrder(id, orderRequestDTO), HttpStatus.OK);
    }

    // DELETE & RECOVER METHOD:
    @PreAuthorize("hasRole('DUENO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> toggleIsEnabled(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.toggleIsEnabled(id), HttpStatus.OK);
    }
    
}

package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.exceptions.ResourceNotFoundException;
import edu.usal.suravicIntegrity.provider.Provider;
import edu.usal.suravicIntegrity.provider.ProviderService;
import edu.usal.suravicIntegrity.user.User;
import edu.usal.suravicIntegrity.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProviderService providerService;
    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrderService(ProviderService providerService, UserService userService, OrderRepository orderRepository) {
        this.providerService = providerService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    // GET METHODS:
    public List<OrderResponseDTO> findOrders(Boolean isEnabled) {
        List<Order> orders = orderRepository.findByIsEnabled(isEnabled);

        return orders.stream()
                .map(OrderMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo encontrar el pedido solicitado con id " + id));
    }

    public OrderResponseDTO findOrderResponseById(Long id) {
        Order order = this.findOrderById(id);

        return OrderMapper.INSTANCE.toDTO((order));
    }

    // CREATE METHOD:
    @Transactional
    public String addOrder(OrderRequestDTO orderRequestDTO) {
        Order order = OrderMapper.INSTANCE.toEntity(orderRequestDTO);

        order.setIsEnabled(true);
        order.setProvider(providerService.findProviderById(orderRequestDTO.getProviderId()));
        order.setUser(userService.findUserById(orderRequestDTO.getUserId()));

        orderRepository.save(order);

        return "Pedido agregado correctamente";
    }

    // PUT METHOD:
    @Transactional
    public String updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
        Order order = this.findOrderById(id);

        Provider updatedProvider = providerService.findProviderById(orderRequestDTO.getProviderId());
        User updatedUser = userService.findUserById(orderRequestDTO.getUserId());

        order.setProvider(updatedProvider);
        order.setUser(updatedUser);
        order.setStatus(orderRequestDTO.getStatus());
        order.setPaymentMethods(orderRequestDTO.getPaymentMethods());
        order.setDeliveryDate(orderRequestDTO.getDeliveryDate());
        order.setTotal(orderRequestDTO.getTotal());
        order.setInvoice(orderRequestDTO.getInvoice());
        order.setPaymentReceipt(orderRequestDTO.getPaymentReceipt());
        orderRepository.save(order);

        return "Pedido actualizado correctamente";
    }

    // DELETE/RECOVER METHOD:
    public String toggleIsEnabled(Long id) {
        Order order = this.findOrderById(id);

        order.setIsEnabled(!order.getIsEnabled());
        orderRepository.save(order);

        return (order.getIsEnabled()) ? "Pedido recuperado correctamente" : "Pedido eliminado correctamente";
    }

}

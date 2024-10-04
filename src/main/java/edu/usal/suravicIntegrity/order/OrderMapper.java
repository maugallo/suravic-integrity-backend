package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.provider.ProviderMapper;
import edu.usal.suravicIntegrity.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());

        dto.setProvider(ProviderMapper.INSTANCE.toDTO(order.getProvider()));
        dto.setUser(UserMapper.INSTANCE.toDTO(order.getUser()));

        dto.setStatus(order.getStatus());
        dto.setPaymentMethods(order.getPaymentMethods());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setTotal(order.getTotal());
        dto.setInvoice(order.getInvoice());
        dto.setPaymentReceipt(order.getPaymentReceipt());

        return dto;
    }

    default Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setStatus(dto.getStatus());
        order.setPaymentMethods(dto.getPaymentMethods());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setTotal(dto.getTotal());
        order.setInvoice(dto.getInvoice());
        order.setPaymentReceipt(dto.getPaymentReceipt());

        return order;
    }

}

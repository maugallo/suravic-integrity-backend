package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.provider.ProviderResponseDTO;
import edu.usal.suravicIntegrity.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;

    private ProviderResponseDTO provider;

    private UserResponseDTO user;

    private String status;

    private Set<String> paymentMethod;

    private LocalDate deliveryDate;

    private Double total;

    private Byte[] invoice;

    private Byte[] paymentReceipt;

}

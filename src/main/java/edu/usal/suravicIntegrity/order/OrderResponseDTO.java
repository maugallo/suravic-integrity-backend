package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.provider.ProviderResponseDTO;
import edu.usal.suravicIntegrity.user.UserResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;

    private ProviderResponseDTO provider;

    private UserResponseDTO user;

    private String status;

    private Set<String> paymentMethods;

    private LocalDate deliveryDate;

    private Double total;

    private Byte[] invoice;

    private Byte[] paymentReceipt;

}

package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.validation.EnumValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class OrderRequestDTO {

    private Long providerId;

    private Long userId;

    @EnumValidator(enumClass = OrderStatus.class)
    private String status;

    @EnumValidator(enumClass = PaymentMethod.class)
    private Set<String> paymentMethod;

    @NotNull
    private LocalDate deliveryDate;

    @NotNull
    @Min(value = 1)
    @Max(value = 9999999)
    private Double total;

    @NotNull
    private Byte[] invoice;

    @NotNull
    private Byte[] paymentReceipt;

}

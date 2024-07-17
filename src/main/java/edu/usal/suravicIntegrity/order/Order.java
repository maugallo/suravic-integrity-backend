package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.provider.Provider;
import edu.usal.suravicIntegrity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(targetClass = PaymentMethod.class) // Indica que PaymentMethod no será una entidad en la base de datos, sino una colección de valores básicos que van como atributo.
    @CollectionTable(name = "orders_payment_methods", joinColumns = @JoinColumn(name = "order_id")) // Define que una nueva tabla llamada "orders_payment_methods" es la que
    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @NotBlank
    @Column
    private LocalDate deliveryDate;

    @NotBlank
    @Min(value = 1)
    @Max(value = 999999999)
    @Column
    private Double total;

    @Lob
    @Column
    private Byte[] invoice;

    @Lob
    @Column
    private Byte[] paymentReceipt;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;

    public Order() {}

    public Order(Long id, OrderStatus status, Provider provider, User user, Set<PaymentMethod> paymentMethods, LocalDate deliveryDate, Double total, Byte[] invoice, Byte[] paymentReceipt, Timestamp createdAt, Timestamp updatedAt, Boolean isEnabled) {
        this.id = id;
        this.status = status;
        this.provider = provider;
        this.user = user;
        this.paymentMethods = paymentMethods;
        this.deliveryDate = deliveryDate;
        this.total = total;
        this.invoice = invoice;
        this.paymentReceipt = paymentReceipt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Byte[] getInvoice() {
        return invoice;
    }

    public void setInvoice(Byte[] invoice) {
        this.invoice = invoice;
    }

    public Byte[] getPaymentReceipt() {
        return paymentReceipt;
    }

    public void setPaymentReceipt(Byte[] paymentReceipt) {
        this.paymentReceipt = paymentReceipt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}

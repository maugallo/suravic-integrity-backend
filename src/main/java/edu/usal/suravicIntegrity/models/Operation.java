package edu.usal.suravicIntegrity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "credit_account_id")
    private CreditAccount creditAccount;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Min(value = 1)
    @Max(value = 999999)
    @Column
    private Double total;

    @Lob
    @Column
    private Byte[] receipt;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;

    public Operation() {}

    public Operation(Long id, CreditAccount creditAccount, OperationType type, User user, Double total, Byte[] receipt, Timestamp createdAt, Timestamp updatedAt, Boolean isEnabled) {
        this.id = id;
        this.creditAccount = creditAccount;
        this.type = type;
        this.user = user;
        this.total = total;
        this.receipt = receipt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public CreditAccount getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(CreditAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(Byte[] receipt) {
        this.receipt = receipt;
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

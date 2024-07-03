package edu.usal.suravicIntegrity.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "percentages")
public class Percentages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "percentage_id")
    private Long id;

    @NotBlank
    @Min(value = 0)
    @Max(value = 9999999)
    @Column
    private Double vatPercentage;

    @NotBlank
    @Min(value = 0)
    @Max(value = 9999999)
    @Column
    private Double profitPercentage;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Percentages() {}

    public Percentages(Long id, Double vatPercentage, Double profitPercentage, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.vatPercentage = vatPercentage;
        this.profitPercentage = profitPercentage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(Double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
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
}

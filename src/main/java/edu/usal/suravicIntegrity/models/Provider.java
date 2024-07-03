package edu.usal.suravicIntegrity.models;

import java.sql.Timestamp;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne
    @JoinColumn(name = "percentages_id")
    private Percentages percentages;

    @Enumerated(EnumType.STRING)
    private VatCondition vatCondition;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]+$")
    @Column(unique = true)
    private String companyName;

    @NotBlank
    @Size(max = 40)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$")
    @Column
    private String firstName;

    @NotBlank
    @Size(max = 40)
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+$")
    @Column
    private String lastName;

    @NotBlank
    @Size(min = 13, max = 13)
    @Pattern(regexp = "^[0-9]{2}-[0-9]{8}-[0-9]$")
    @Column(unique = true)
    private String cuit;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;

    public Provider() {}

    public Provider(Long id, Sector sector, Contact contact, Percentages percentages, VatCondition vatCondition, String companyName, String firstName, String lastName, String cuit, Timestamp createdAt, Timestamp updatedAt, Boolean isEnabled) {
        this.id = id;
        this.sector = sector;
        this.contact = contact;
        this.percentages = percentages;
        this.vatCondition = vatCondition;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cuit = cuit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {}

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Percentages getPercentages() {
        return percentages;
    }

    public void setPercentages(Percentages percentages) {
        this.percentages = percentages;
    }

    public VatCondition getVatCondition() {
        return vatCondition;
    }

    public void setVatCondition(VatCondition vatCondition) {
        this.vatCondition = vatCondition;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
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

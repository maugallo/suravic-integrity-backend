package edu.usal.suravicIntegrity.provider;

import java.sql.Timestamp;

import edu.usal.suravicIntegrity.contact.Contact;
import edu.usal.suravicIntegrity.sector.Sector;
import edu.usal.suravicIntegrity.percentages.Percentages;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "providers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column
    private String vatCondition;

    @Column(unique = true)
    private String companyName;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String cuit;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;

}
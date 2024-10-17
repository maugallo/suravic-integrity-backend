package edu.usal.suravicIntegrity.meatDetails;

import edu.usal.suravicIntegrity.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meat_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeatDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meat_details_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private Double percentage;

    @Column
    private Double weight;

}

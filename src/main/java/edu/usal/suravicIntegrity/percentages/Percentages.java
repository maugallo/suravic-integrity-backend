package edu.usal.suravicIntegrity.percentages;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "percentages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Percentages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "percentage_id")
    private Long id;

    @Column
    private Double vatPercentage;

    @Column
    private Double profitPercentage;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}

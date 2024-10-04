package edu.usal.suravicIntegrity.order;

import edu.usal.suravicIntegrity.provider.Provider;
import edu.usal.suravicIntegrity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String status;

    @ElementCollection
    private Set<String> paymentMethod;

    @Column
    private LocalDate deliveryDate;

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
}

package edu.usal.suravicIntegrity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "public_holidays")
public class PublicHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "public_holiday_id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "public_holidays_shift",
            joinColumns = @JoinColumn(name = "public_holiday_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id")
    )
    private Set<Shift> shifts = new HashSet<>();

    @NotBlank
    @Column
    private LocalDate date;

    @NotBlank
    @Size(max = 100)
    @Column
    private String reason;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;



}

package edu.usal.suravicIntegrity.shift;

import edu.usal.suravicIntegrity.dayOff.DayOff;
import edu.usal.suravicIntegrity.employee.Employee;
import edu.usal.suravicIntegrity.publicHoliday.PublicHoliday;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Long id;

    @ManyToMany(mappedBy = "shifts")
    private Set<DayOff> daysOff = new HashSet<>();

    @ManyToMany(mappedBy = "shifts")
    private Set<PublicHoliday> publicHolidays = new HashSet<>();

    @ManyToMany(mappedBy = "shifts")
    private Set<Employee> employees = new HashSet<>();

    @NotBlank
    @Column
    private LocalTime startTime;

    @NotBlank
    @Column
    private LocalTime endTime;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;

    public Shift() {}

    public Shift(Long id, Set<DayOff> daysOff, Set<PublicHoliday> publicHolidays, Set<Employee> employees, LocalTime startTime, LocalTime endTime, Timestamp createdAt, Timestamp updatedAt, Boolean isEnabled) {
        this.id = id;
        this.daysOff = daysOff;
        this.publicHolidays = publicHolidays;
        this.employees = employees;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public Set<DayOff> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(Set<DayOff> daysOff) {
        this.daysOff = daysOff;
    }

    public Set<PublicHoliday> getPublicHolidays() {
        return publicHolidays;
    }

    public void setPublicHolidays(Set<PublicHoliday> publicHolidays) {
        this.publicHolidays = publicHolidays;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

package edu.usal.suravicIntegrity.attendance;

import edu.usal.suravicIntegrity.employee.Employee;
import jakarta.persistence.*;

import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column
    private LocalDateTime checkIn;

    @Column
    private LocalDateTime checkOut;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public Attendance() {}

    public Attendance(Long id, Employee employee, AttendanceStatus status, LocalDateTime checkIn, LocalDateTime checkOut, Timestamp updatedAt) {
        this.id = id;
        this.employee = employee;
        this.status = status;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

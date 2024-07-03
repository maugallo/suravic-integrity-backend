package edu.usal.suravicIntegrity.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToMany
    @JoinTable(
            name = "employees_days_off",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "day_off_id")
    )
    private Set<DayOff> daysOff = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "employees_shifts",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "shift_id")
    )
    private Set<Shift> shifts = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "employees_clothing_items", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<EmployeesClothingItems> clothingItems = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;

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
    @Pattern(regexp = "^[0-9]{8}$")
    @Column(unique = true)
    private String dni;

    @NotBlank
    @Size(min = 5, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]+$")
    @Column
    private String street;

    @NotBlank
    @Size(min = 1, max = 6)
    @Pattern(regexp = "^[0-9]+$")
    @Column
    private String num;

    @NotBlank
    @Size(min = 1, max = 6)
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Column
    private String zipCode;

    @NotBlank
    @Column
    private LocalDate birthDate;

    @NotBlank
    @Column
    private LocalDate hireDate;

    @Column
    private LocalDate terminationDate;

    @NotBlank
    @Column
    private Integer vacationDays;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column
    private Boolean isEnabled;

    public Employee() {}

    public Employee(Long id, Contact contact, Set<Shift> shifts, Set<DayOff> daysOff, Set<EmployeesClothingItems> clothingItems, EmployeeRole employeeRole, String firstName, String lastName, String dni, String street, String num, String zipCode, LocalDate hireDate, LocalDate terminationDate, Integer vacationDays, LocalDate birthDate, Timestamp createdAt, Timestamp updatedAt, Boolean isEnabled) {
        this.id = id;
        this.contact = contact;
        this.shifts = shifts;
        this.daysOff = daysOff;
        this.clothingItems = clothingItems;
        this.employeeRole = employeeRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.street = street;
        this.num = num;
        this.zipCode = zipCode;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.vacationDays = vacationDays;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Set<DayOff> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(Set<DayOff> daysOff) {
        this.daysOff = daysOff;
    }

    public Set<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Set<Shift> shifts) {
        this.shifts = shifts;
    }

    public Set<EmployeesClothingItems> getClothingItems() {
        return clothingItems;
    }

    public void setClothingItems(Set<EmployeesClothingItems> clothingItems) {
        this.clothingItems = clothingItems;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
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

package pe.edu.utp.librarysystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String fullName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @Column(nullable = true)
    private boolean active = true;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date hireDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String position;

    @PrePersist
    protected void onCreate() {
        this.hireDate = new Date();
    }
}

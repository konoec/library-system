package pe.edu.utp.librarysystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false, unique=true)
    private String username;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 200,nullable = false)
    private String role;
    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;
}

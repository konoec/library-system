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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500, nullable = false, unique=true)
    private String fullName;
    @Column(length = 15, unique=true)
    private String phone;
    @Column(length = 500, unique=true)
    private String email;
    @Column(length = 500)
    private String address;
    @Column(nullable = true)
    private boolean active = true;

}

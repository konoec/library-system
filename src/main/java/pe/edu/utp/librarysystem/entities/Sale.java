package pe.edu.utp.librarysystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;
    @Column(nullable = false, updatable = false)
    private Date saleDate;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSale;

    @PrePersist
    protected void onCreate() {
        this.saleDate = new Date();
    }
}

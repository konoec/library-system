package pe.edu.utp.librarysystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "salesId")
    private Sales sales;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
}

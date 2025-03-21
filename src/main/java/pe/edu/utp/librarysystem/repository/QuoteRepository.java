package pe.edu.utp.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.librarysystem.entities.Quote;
import pe.edu.utp.librarysystem.entities.Sale;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByClientFullNameContainingIgnoreCaseAndEmployeeFullNameContainingIgnoreCase(String clientName, String employeeName);
}

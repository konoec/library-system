package pe.edu.utp.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.librarysystem.entities.Quote;
import pe.edu.utp.librarysystem.entities.QuoteDetail;

@Repository
public interface QuoteDetailRepository extends JpaRepository<QuoteDetail, Long> {
    void deleteByQuote(Quote quote);
}

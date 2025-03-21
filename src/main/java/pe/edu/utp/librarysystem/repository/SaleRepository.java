package pe.edu.utp.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.librarysystem.entities.Sale;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {
}

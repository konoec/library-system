package pe.edu.utp.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends JpaRepository<pe.edu.utp.librarysystem.entities.Users, Long> {
}

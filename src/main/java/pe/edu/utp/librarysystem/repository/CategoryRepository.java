package pe.edu.utp.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.librarysystem.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    List<Category> findByActiveTrue();
}

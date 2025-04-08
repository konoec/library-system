package pe.edu.utp.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.librarysystem.entities.Employee;
import pe.edu.utp.librarysystem.entities.Product;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFullNameContainingIgnoreCaseAndActiveTrue(String fullName);
    List<Employee> findByActiveTrue();
}

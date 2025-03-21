package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.librarysystem.entities.Employee;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public List<Employee> listEmployees() {
        return employeeRepository.findByActiveTrue();
    }

    public void updateEmployee(Long id, Employee employee) {
        Employee employeeToUpdate = employeeRepository.findById(id).orElse(null);
        if (employeeToUpdate != null) {
            employeeToUpdate.setFullName(employee.getFullName());
            employeeToUpdate.setAddress(employee.getAddress());
            employeeToUpdate.setEmail(employee.getEmail());
            employeeToUpdate.setDepartment(employee.getDepartment());
            employeeToUpdate.setPosition(employee.getPosition());
            employeeToUpdate.setPhoneNumber(employee.getPhoneNumber());
            employeeRepository.save(employeeToUpdate);
        }
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public void deleteEmployee(Long id) throws Exception {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new Exception("Empleado no encontrado"));
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    public List<Employee> searchEmployees(String fullName) {
        return employeeRepository.findByFullNameContainingIgnoreCaseAndActiveTrue(fullName);
    }
}

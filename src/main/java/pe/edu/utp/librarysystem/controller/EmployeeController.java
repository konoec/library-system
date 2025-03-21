package pe.edu.utp.librarysystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.Client;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.entities.Employee;
import pe.edu.utp.librarysystem.services.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public String showEmployees(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Employee> employees = employeeService.listEmployees();
        model.addAttribute("employees", employees);
        return "listEmployees";
    }

    @GetMapping("/create")
    public String showFormCreateEmployee(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        model.addAttribute("employee", new Employee());
        model.addAttribute("action", "/employee/create");
        return "formCreateEmployee";
    }

    @PostMapping("/create")
    public String saveEmployee(@ModelAttribute Employee employee, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        employeeService.saveEmployee(employee);
        return "redirect:/employee";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateEmployee(@PathVariable Long id, @ModelAttribute Employee employee, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("action", "/employee/update/" + id);
        return "formUpdateEmployee";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        employeeService.updateEmployee(id, employee);
        return "redirect:/employee"; //dashboard //redirect:/employee
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, HttpSession session) throws Exception {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        employeeService.deleteEmployee(id);
        return "redirect:/employee";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam("fullName") String fullName, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Employee> employees = employeeService.searchEmployees(fullName);
        model.addAttribute("employees", employees);
        return "listEmployees";
    }
}

package pe.edu.utp.librarysystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.Client;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.services.CredentialService;
import pe.edu.utp.librarysystem.services.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/")
public class DashboardController {
    @Autowired
    private CredentialService credentialService;

    @GetMapping("/dashboard")
    public String showCredentials(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Credential> credentials = credentialService.listCredentials();
        model.addAttribute("credentials", credentials);
        return "index"; //index
    }

    @GetMapping("/")
    public String showPage(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Credential> credentials = credentialService.listCredentials();
        model.addAttribute("credentials", credentials);
        return "redirect:/dashboard"; //index
    }
}
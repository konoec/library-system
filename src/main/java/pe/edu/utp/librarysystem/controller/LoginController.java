package pe.edu.utp.librarysystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.services.CredentialService;

@Controller
public class LoginController {

    @Autowired
    private CredentialService credentialService; // Asume que tienes un servicio para manejar las credenciales

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Renderiza el formulario de inicio de sesión (login.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Credential credential = credentialService.authenticateUser(username, password);

        if (credential != null) {
            // Usuario autenticado correctamente
            session.setAttribute("loggedInUser", credential); // Guarda el usuario en la sesión

            if (credential.getRole().equals("ADMIN")){
                return "redirect:/dashboard"; // Redirige a la página de inicio o dashboard /credential
            }

            if (credential.getRole().equals("USER")){
                return "redirect:/category"; // Redirige a la página de inicio o dashboard
            }

            return "login";
        } else {
            // Usuario no autenticado, mostrar mensaje de error
            model.addAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
            return "login"; // Vuelve a mostrar el formulario de inicio de sesión con mensaje de error
        }
    }

    // Método para cerrar sesión (logout)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión actual
        return "redirect:/login"; // Redirige al formulario de login
    }
}

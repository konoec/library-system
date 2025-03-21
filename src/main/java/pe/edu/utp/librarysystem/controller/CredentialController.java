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
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public String showCredentials(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Credential> credentials = credentialService.listCredentials();
        model.addAttribute("credentials", credentials);
        return "listCredentials";
    }



    @GetMapping("/create")
    public String showFormCreateCredential(Model model, HttpSession session) {
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        model.addAttribute("credential", new Credential());
        model.addAttribute("employees", employeeService.listEmployees());
        model.addAttribute("action", "/credential/create");
        return "formCreateCredential";
    }

    @PostMapping("/create")
    public String saveCredential(@ModelAttribute Credential credential, HttpSession session) {
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        credentialService.saveCredential(credential);
        return "redirect:/credential";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateCredential(@PathVariable Long id, @ModelAttribute Credential credential, Model model, HttpSession session) {
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        credential = credentialService.findById(id);
        model.addAttribute("credential", credential);
        model.addAttribute("employees", employeeService.listEmployees());
        model.addAttribute("action", "/credential/update/" + id);
        return "formUpdateCredential";
    }

    @PostMapping("/update/{id}")
    public String updateCredential(@PathVariable Long id, @ModelAttribute Credential credential, HttpSession session) {
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        credentialService.updateCredential(id, credential);
        return "redirect:/credential";
    }

    @GetMapping("/delete/{id}")
    public String deleteCredential(@PathVariable Long id, HttpSession session){
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        credentialService.deleteCredential(id);
        return "redirect:/credential";
    }

    @GetMapping("/search")
    public String searchCredential(@RequestParam("username") String username, Model model, HttpSession session) {
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole().equals("USER")) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Credential> credentials = credentialService.searchCredentials(username);
        model.addAttribute("credentials", credentials);
        return "listCredentials";
    }
}

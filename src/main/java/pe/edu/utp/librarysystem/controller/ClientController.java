package pe.edu.utp.librarysystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.Client;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.services.ClientService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping()
    public String showClients(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Client> clients = clientService.listClients();
        model.addAttribute("clients", clients);
        return "listClients";
    }

    @GetMapping("/create")
    public String showFormCreateClient(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        model.addAttribute("client", new Client());
        model.addAttribute("action", "/client/create");
        return "formCreateClient";
    }

    @PostMapping("/create")
    public String saveClient(@ModelAttribute Client client, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        clientService.saveClient(client);
        return "redirect:/client";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateClient(@PathVariable Long id, @ModelAttribute Client client, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        client = clientService.findById(id);
        model.addAttribute("client", client);
        model.addAttribute("action", "/client/update/" + id);
        return "formUpdateClient";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Client client, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        clientService.updateClient(id, client);
        return "redirect:/client";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id, HttpSession session) throws Exception {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        clientService.deleteClient(id);
        return "redirect:/client";
    }

    @GetMapping("/search")
    public String searchClients(@RequestParam("fullName") String fullName, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Client> clients = clientService.searchClients(fullName);
        model.addAttribute("clients", clients);
        return "listClients";
    }
}

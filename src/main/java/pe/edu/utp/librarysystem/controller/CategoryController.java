package pe.edu.utp.librarysystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.Category;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.services.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String showCategories(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        // Si hay usuario autenticado, continúa con la lógica del controlador
        List<Category> categories = categoryService.listCategories();
        model.addAttribute("categories", categories);
        return "listCategories";
    }

    @GetMapping("/create")
    public String showFormCreateCategory(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        model.addAttribute("category", new Category());
        model.addAttribute("action", "/category/create");
        return "formCreateCategory";
    }

    @PostMapping("/create")
    public String saveEmployee(@ModelAttribute Category category, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        categoryService.saveCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateEmployee(@PathVariable Long id, @ModelAttribute Category category, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        category = categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("action", "/category/update/" + id);
        return "formUpdateCategory";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Category category, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        categoryService.updateCategory(id, category);
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, HttpSession session) throws Exception {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        categoryService.deleteCategory(id);
        return "redirect:/category";
    }

    @GetMapping("/search")
    public String searchCategories(@RequestParam("name") String name, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Category> categories = categoryService.searchCategories(name);
        model.addAttribute("categories", categories);
        return "listCategories";
    }
}

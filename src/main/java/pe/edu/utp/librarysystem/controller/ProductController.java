package pe.edu.utp.librarysystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.Credential;
import pe.edu.utp.librarysystem.entities.Employee;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.services.CategoryService;
import pe.edu.utp.librarysystem.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String showEmployees(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Product> products = productService.listProducts();
        model.addAttribute("products", products);
        return "listProducts";
    }

    @GetMapping("/create")
    public String showFormCreateProduct(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        model.addAttribute("product", new Product());
        model.addAttribute("action", "/product/create");
        model.addAttribute("categories", categoryService.listCategories());
        return "formCreateProduct";
    }

    @PostMapping("/create")
    public String saveEmployee(@ModelAttribute Product product, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        productService.saveProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateEmployee(@PathVariable Long id, @ModelAttribute Product product, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("action", "/product/update/" + id);
        model.addAttribute("categories", categoryService.listCategories());
        return "formUpdateProduct";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Product product, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        productService.updateProduct(id, product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) throws Exception {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        productService.deleteProduct(id);
        return "redirect:/product";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("name") String name, @RequestParam("category") String category, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Product> products = productService.searchProducts(name, category);
        model.addAttribute("products", products);
        return "listProducts";
    }
}

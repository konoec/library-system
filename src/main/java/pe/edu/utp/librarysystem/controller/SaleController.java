package pe.edu.utp.librarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.Client;
import pe.edu.utp.librarysystem.entities.Employee;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.entities.Sale;
import pe.edu.utp.librarysystem.services.ClientService;
import pe.edu.utp.librarysystem.services.EmployeeService;
import pe.edu.utp.librarysystem.services.ProductService;
import pe.edu.utp.librarysystem.services.SalesService;

import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String listSales(Model model) {
        List<Sale> sales = salesService.listSales();
        model.addAttribute("sales", sales);
        return "listSales";
    }

    @GetMapping("/create")
    public String showFormCreateSale(Model model) {
        List<Client> clients = clientService.listClients();
        List<Employee> employees = employeeService.listEmployees();
        List<Product> products = productService.listProducts();
        
        model.addAttribute("sale", new Sale());
        model.addAttribute("clients", clients);
        model.addAttribute("employees", employees);
        model.addAttribute("products", products);
        
        return "formCreateSale";
    }

    @PostMapping("/create")
    public String saveSale(@ModelAttribute Sale sale, @RequestParam("productIds") List<Long> productIds, @RequestParam("amounts") List<Integer> amounts) {
        salesService.saveSale(sale, productIds, amounts);
        return "redirect:/sales";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateSale(@PathVariable Long id, Model model) {
        Sale sale = salesService.findById(id);
        List<Client> clients = clientService.listClients();
        List<Employee> employees = employeeService.listEmployees();
        List<Product> products = productService.listProducts();
        
        model.addAttribute("sale", sale);
        model.addAttribute("clients", clients);
        model.addAttribute("employees", employees);
        model.addAttribute("products", products);
        
        return "formUpdateSale";
    }

    @PostMapping("/update/{id}")
    public String updateSale(@ModelAttribute Sale sale, @PathVariable Long id, @RequestParam("productIds") List<Long> productIds, @RequestParam("amounts") List<Integer> amounts) {
        salesService.updateSale(sale, productIds, amounts);
        return "redirect:/sales";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        salesService.deleteSale(id);
        return "redirect:/sales";
    }
}

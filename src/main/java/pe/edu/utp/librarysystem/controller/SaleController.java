package pe.edu.utp.librarysystem.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.librarysystem.entities.*;
import pe.edu.utp.librarysystem.services.ClientService;
import pe.edu.utp.librarysystem.services.EmployeeService;
import pe.edu.utp.librarysystem.services.ProductService;
import pe.edu.utp.librarysystem.services.SaleService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String listSales(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Sale> sales = saleService.listSales();
        model.addAttribute("sales", sales);
        return "listSales";
    }

    @GetMapping("/search")
    public String searchSales(@RequestParam(value = "clientName", required = false) String clientName,
                              @RequestParam(value = "employeeName", required = false) String employeeName,
                              Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Sale> sales = saleService.searchSales(clientName, employeeName);
        model.addAttribute("sales", sales);
        return "listSales";
    }

    @GetMapping("/create")
    public String showFormCreateSale(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

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
    public String createSale(@ModelAttribute("sale") Sale sale,
                             @RequestParam("clientId") Long clientId,
                             @RequestParam("employeeId") Long employeeId,
                             @RequestParam("productIds") List<Long> productIds,
                             @RequestParam("amounts") List<Integer> amounts, HttpSession session) {

        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        // Asignar el cliente y empleado a la venta actualizada
        Client client = clientService.findById(clientId);
        Employee employee = employeeService.findById(employeeId);

        sale.setClient(client);
        sale.setEmployee(employee);

        // Llama al servicio para actualizar la venta
        saleService.saveSale(sale, productIds, amounts);

        return "redirect:/sale";
    }

    @PostMapping("/update/{id}")
    public String updateSale(@PathVariable("id") Long id,
                             @ModelAttribute("sale") Sale updatedSale,
                             @RequestParam("clientId") Long clientId,
                             @RequestParam("employeeId") Long employeeId,
                             @RequestParam("productIds") List<Long> productIds,
                             @RequestParam("amounts") List<Integer> amounts, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        // Asignar el cliente y empleado a la venta actualizada
        Client client = clientService.findById(clientId);
        Employee employee = employeeService.findById(employeeId);

        updatedSale.setClient(client);
        updatedSale.setEmployee(employee);

        // Llama al servicio para actualizar la venta
        saleService.updateSale(updatedSale, productIds, amounts);

        return "redirect:/sale/view/" + id; // Redirige a la página de detalles de la venta actualizada
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateSale(@PathVariable Long id, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        Sale sale = saleService.findById(id);
        List<Client> clients = clientService.listClients();
        List<Employee> employees = employeeService.listEmployees();
        List<Product> products = productService.listProducts();
        
        model.addAttribute("sale", sale);
        model.addAttribute("clients", clients);
        model.addAttribute("employees", employees);
        model.addAttribute("products", products);
        
        return "formUpdateSale";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        saleService.deleteSale(id);
        return "redirect:/sale";
    }

    @GetMapping("/view/{id}")
    public String viewSale(@PathVariable Long id, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        Sale sale = saleService.findById(id);
        model.addAttribute("sale", sale);
        return "viewSale";
    }

    @GetMapping("/generateReport/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable Long id, HttpSession session) throws DocumentException{
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return null;
        }

        Sale sale = saleService.findById(id);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
        Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        document.add(new Paragraph("SERVICIOS PROFESIONALES AARON", titleFont));
        document.add(new Paragraph("RUC: 10417853401", bodyFont));
        document.add(new Paragraph("PJ. Nelly Castillo Chirinos, Mz. l Lote 30", bodyFont));
        document.add(new Paragraph(" ", bodyFont));

        document.add(new Paragraph("Detalles de la Venta", subTitleFont));
        document.add(new Paragraph("Venta ID: " + sale.getId(), bodyFont));
        document.add(new Paragraph("Cliente: " + sale.getClient().getFullName(), bodyFont));
        document.add(new Paragraph("Empleado: " + sale.getEmployee().getFullName(), bodyFont));
        document.add(new Paragraph("Fecha de Venta: " + sale.getSaleDate(), bodyFont));
        document.add(new Paragraph("Total de la Venta: " + sale.getTotalSale(), bodyFont));
        document.add(new Paragraph(" ", bodyFont));

        document.add(new Paragraph("Detalles de la Venta", headerFont));
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        PdfPCell cell = new PdfPCell(new Phrase("Producto", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cantidad", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Precio Unitario", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Subtotal", headerFont));
        table.addCell(cell);

        for (SaleDetail detail : sale.getSaleDetails()) {
            table.addCell(new PdfPCell(new Phrase(detail.getProduct().getName(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(detail.getAmount().toString(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(detail.getUnitPrice().toString(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(detail.getUnitPrice().multiply(new BigDecimal(detail.getAmount())).toString(), bodyFont)));
        }

        document.add(table);

        document.close();

        byte[] pdfBytes = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "venta_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

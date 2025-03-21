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
import pe.edu.utp.librarysystem.services.QuoteService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String listQuotes(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Quote> quotes = quoteService.listQuotes();
        model.addAttribute("quotes", quotes);
        return "listQuotes";
    }

    @GetMapping("/create")
    public String showFormCreateQuote(Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Client> clients = clientService.listClients();
        List<Employee> employees = employeeService.listEmployees();
        List<Product> products = productService.listProducts();
        
        model.addAttribute("quote", new Quote());
        model.addAttribute("clients", clients);
        model.addAttribute("employees", employees);
        model.addAttribute("products", products);
        
        return "formCreateQuote";
    }

    @PostMapping("/create")
    public String createQuote(@ModelAttribute("quote") Quote quote,
                              @RequestParam("clientId") Long clientId,
                              @RequestParam("employeeId") Long employeeId,
                              @RequestParam("productIds") List<Long> productIds,
                              @RequestParam("quantities") List<Integer> quantities, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }


        // Asignar el cliente y empleado a la cotización
        Client client = clientService.findById(clientId);
        Employee employee = employeeService.findById(employeeId);

        quote.setClient(client);
        quote.setEmployee(employee);

        // Llama al servicio para guardar la cotización
        quoteService.saveQuote(quote, productIds, quantities);

        return "redirect:/quote";
    }

    @GetMapping("/search")
    public String searchQuotes(@RequestParam(value = "clientName", required = false) String clientName,
                              @RequestParam(value = "employeeName", required = false) String employeeName,
                              Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        List<Quote> quotes = quoteService.searchQuotes(clientName, employeeName);
        model.addAttribute("quotes", quotes);
        return "listQuotes";
    }

    @PostMapping("/update/{id}")
    public String updateQuote(@PathVariable("id") Long id,
                              @ModelAttribute("quote") Quote updatedQuote,
                              @RequestParam("clientId") Long clientId,
                              @RequestParam("employeeId") Long employeeId,
                              @RequestParam("productIds") List<Long> productIds,
                              @RequestParam("quantities") List<Integer> quantities,
                              HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        // Asignar el cliente y empleado a la cotización actualizada
        Client client = clientService.findById(clientId);
        Employee employee = employeeService.findById(employeeId);

        updatedQuote.setClient(client);
        updatedQuote.setEmployee(employee);

        // Llama al servicio para actualizar la cotización
        quoteService.updateQuote(updatedQuote, productIds, quantities);

        return "redirect:/quote/view/" + id; // Redirige a la página de detalles de la cotización actualizada
    }

    @GetMapping("/update/{id}")
    public String showFormUpdateQuote(@PathVariable Long id, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        Quote quote = quoteService.findById(id);
        List<Client> clients = clientService.listClients();
        List<Employee> employees = employeeService.listEmployees();
        List<Product> products = productService.listProducts();
        
        model.addAttribute("quote", quote);
        model.addAttribute("clients", clients);
        model.addAttribute("employees", employees);
        model.addAttribute("products", products);
        
        return "formUpdateQuote";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuote(@PathVariable Long id, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        quoteService.deleteQuote(id);
        return "redirect:/quote";
    }

    @GetMapping("/view/{id}")
    public String viewQuote(@PathVariable Long id, Model model, HttpSession session) {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return "redirect:/login";
        }

        Quote quote = quoteService.findById(id);
        model.addAttribute("quote", quote);
        return "viewQuote";
    }

    @GetMapping("/generateReport/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable Long id, HttpSession session) throws DocumentException, IOException {
        // Verifica si hay un usuario autenticado en sesión
        Credential loggedInUser = (Credential) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Si no hay usuario autenticado, redirige al formulario de inicio de sesión
            return null;
        }

        Quote quote = quoteService.findById(id);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        // Estilo de fuentes
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
        Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        // Información de la empresa
        document.add(new Paragraph("SERVICIOS PROFESIONALES AARON", titleFont));
        document.add(new Paragraph("RUC: 10417853401", bodyFont));
        document.add(new Paragraph("PJ. Nelly Castillo Chirinos, Mz. l Lote 30", bodyFont));
        document.add(new Paragraph(" ", bodyFont)); // Espacio en blanco

        // Información de la cotización
        document.add(new Paragraph("Detalles de la Cotización", subTitleFont));
        document.add(new Paragraph("Cotización ID: " + quote.getId(), bodyFont));
        document.add(new Paragraph("Cliente: " + quote.getClient().getFullName(), bodyFont));
        document.add(new Paragraph("Empleado: " + quote.getEmployee().getFullName(), bodyFont));
        document.add(new Paragraph("Fecha de Cotización: " + quote.getQuoteDate(), bodyFont));
        document.add(new Paragraph("Total de la Cotización: " + quote.getTotalQuote(), bodyFont));
        document.add(new Paragraph(" ", bodyFont)); // Espacio en blanco

        // Detalles de la cotización
        document.add(new Paragraph("Detalles de la Cotización", headerFont));
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Encabezados de la tabla
        PdfPCell cell = new PdfPCell(new Phrase("Producto", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cantidad", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Precio Unitario", headerFont));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Subtotal", headerFont));
        table.addCell(cell);

        // Datos de la tabla
        for (QuoteDetail detail : quote.getQuoteDetails()) {
            table.addCell(new PdfPCell(new Phrase(detail.getProduct().getName(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(detail.getQuantity().toString(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(detail.getUnitPrice().toString(), bodyFont)));
            table.addCell(new PdfPCell(new Phrase(detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())).toString(), bodyFont)));
        }

        document.add(table);

        document.close();

        byte[] pdfBytes = out.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "cotizacion_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

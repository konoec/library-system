package pe.edu.utp.librarysystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataIntegrityViolationException;
import pe.edu.utp.librarysystem.controller.CredentialController;
import pe.edu.utp.librarysystem.entities.*;
import pe.edu.utp.librarysystem.services.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class LibrarySystemApplication implements ApplicationRunner {
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CredentialController credentialController;

    public static void main(String[] args) {
        SpringApplication.run(LibrarySystemApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Date now = new Date();

        // Empleados
        List<Employee> employees = List.of(
                new Employee(null, "Fernando Diaz", "FernandoDiaz@gmail.com", "912123123", "Av Pacifico", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Maria Lopez", "MariaLopez@gmail.com", "912123124", "Av Sol", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Luis Garcia", "LuisGarcia@gmail.com", "912123125", "Av Luna", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Ana Torres", "AnaTorres@gmail.com", "912123126", "Av Estrella", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Carlos Martinez", "CarlosMartinez@gmail.com", "912123127", "Av Mar", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Lucia Gomez", "LuciaGomez@gmail.com", "912123128", "Av Tierra", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Jorge Ramirez", "JorgeRamirez@gmail.com", "912123129", "Av Fuego", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Patricia Sanchez", "PatriciaSanchez@gmail.com", "912123130", "Av Viento", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Andres Castro", "AndresCastro@gmail.com", "912123131", "Av Agua", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Isabel Morales", "IsabelMorales@gmail.com", "912123132", "Av Montaña", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Rafael Ruiz", "RafaelRuiz@gmail.com", "912123133", "Av Valle", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Valeria Flores", "ValeriaFlores@gmail.com", "912123134", "Av Bosque", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Gustavo Vargas", "GustavoVargas@gmail.com", "912123135", "Av Arena", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Elena Jimenez", "ElenaJimenez@gmail.com", "912123136", "Av Roca", true, now, now, "Ventas", "Cajero"),
                new Employee(null, "Diego Fernandez", "DiegoFernandez@gmail.com", "912123137", "Av Cielo", true, now, now, "Ventas", "Cajero")
        );

        // Credenciales
        List<Credential> credentials = employees.stream().map(e ->
                new Credential(null, e.getFullName().replace(" ", ""), "password123", "ADMIN", e)
        ).toList();

        // Clientes
        List<Client> clients = List.of(
                new Client(null, "Juan Perez", "987654321", "juanperez@example.com", "Calle Falsa 123", true),
                new Client(null, "Laura Martinez", "987654322", "lauramartinez@example.com", "Calle Real 124", true),
                new Client(null, "Miguel Rodriguez", "987654323", "miguelrodriguez@example.com", "Calle Verdadera 125", true),
                new Client(null, "Sofia Hernandez", "987654324", "sofiahernandez@example.com", "Calle Larga 126", true),
                new Client(null, "Daniel Torres", "987654325", "danieltorres@example.com", "Calle Corta 127", true),
                new Client(null, "Carla Ramirez", "987654326", "carlaramirez@example.com", "Calle Ancha 128", true),
                new Client(null, "Jose Gonzalez", "987654327", "josegonzalez@example.com", "Calle Estrecha 129", true),
                new Client(null, "Lucia Morales", "987654328", "luciamorales@example.com", "Calle Principal 130", true),
                new Client(null, "Andres Flores", "987654329", "andresflores@example.com", "Calle Secundaria 131", true),
                new Client(null, "Ana Rios", "987654330", "anarios@example.com", "Calle Terciaria 132", true),
                new Client(null, "Carlos Castro", "987654331", "carloscastro@example.com", "Calle Cuarta 133", true),
                new Client(null, "Valeria Paredes", "987654332", "valeriaparedes@example.com", "Calle Quinta 134", true),
                new Client(null, "Fernando Silva", "987654333", "fernandosilva@example.com", "Calle Sexta 135", true),
                new Client(null, "Elena Mendoza", "987654334", "elenamendoza@example.com", "Calle Septima 136", true),
                new Client(null, "Gustavo Herrera", "987654335", "gustavoherrera@example.com", "Calle Octava 137", true)
        );

        // Categorías
        List<Category> categories = List.of(
                new Category(null, "Libros", "Libros de diversos géneros", true),
                new Category(null, "Revistas", "Revistas de diversas temáticas", true),
                new Category(null, "Música", "CDs y vinilos de música", true),
                new Category(null, "Películas", "DVDs y Blu-rays de películas", true),
                new Category(null, "Juguetes", "Juguetes para todas las edades", true),
                new Category(null, "Electrónica", "Aparatos electrónicos diversos", true),
                new Category(null, "Videojuegos", "Videojuegos para todas las consolas", true),
                new Category(null, "Ropa", "Ropa para todas las edades y géneros", true),
                new Category(null, "Calzado", "Calzado para todas las edades y géneros", true),
                new Category(null, "Accesorios", "Accesorios de moda", true),
                new Category(null, "Papelería", "Artículos de papelería", true),
                new Category(null, "Muebles", "Muebles para el hogar y oficina", true),
                new Category(null, "Deportes", "Artículos deportivos", true),
                new Category(null, "Hogar", "Artículos para el hogar", true),
                new Category(null, "Jardinería", "Artículos de jardinería", true)
        );

        // Productos
        List<Product> products = List.of(
                new Product(null, "El Quijote", "Novela de Miguel de Cervantes", new BigDecimal("99.99"), 10, true, categories.get(0)),
                new Product(null, "1984", "Novela de George Orwell", new BigDecimal("79.99"), 15, true, categories.get(0)),
                new Product(null, "Revista Time", "Edición semanal de la revista Time", new BigDecimal("19.99"), 30, true, categories.get(1)),
                new Product(null, "Thriller", "Álbum de Michael Jackson", new BigDecimal("29.99"), 20, true, categories.get(2)),
                new Product(null, "Inception", "Película de Christopher Nolan", new BigDecimal("14.99"), 25, true, categories.get(3)),
                new Product(null, "LEGO Star Wars", "Set de construcción de LEGO", new BigDecimal("59.99"), 10, true, categories.get(4)),
                new Product(null, "Smartphone", "Teléfono inteligente de última generación", new BigDecimal("499.99"), 5, true, categories.get(5)),
                new Product(null, "PlayStation 5", "Consola de videojuegos de Sony", new BigDecimal("499.99"), 8, true, categories.get(6)),
                new Product(null, "Camiseta", "Camiseta de algodón", new BigDecimal("9.99"), 50, true, categories.get(7)),
                new Product(null, "Zapatillas", "Zapatillas deportivas", new BigDecimal("49.99"), 20, true, categories.get(8)),
                new Product(null, "Reloj de pulsera", "Reloj de pulsera para hombre", new BigDecimal("199.99"), 10, true, categories.get(9)),
                new Product(null, "Cuaderno", "Cuaderno de 100 hojas", new BigDecimal("4.99"), 100, true, categories.get(10)),
                new Product(null, "Escritorio", "Escritorio de oficina", new BigDecimal("199.99"), 3, true, categories.get(11)),
                new Product(null, "Bicicleta", "Bicicleta de montaña", new BigDecimal("299.99"), 7, true, categories.get(12)),
                new Product(null, "Cafetera", "Cafetera eléctrica", new BigDecimal("49.99"), 15, true, categories.get(13))
        );

        for (Employee employee : employees) {
            try {
                employeeService.saveEmployee(employee);
            } catch (DataIntegrityViolationException ignored) {
            }
        }

        for (Credential credential : credentials) {
            try {
                credentialService.saveCredential(credential);
            } catch (DataIntegrityViolationException ignored) {
            }
        }

        for (Client client : clients) {
            try {
                clientService.saveClient(client);
            } catch (DataIntegrityViolationException ignored) {
            }
        }

        for (Category category : categories) {
            try {
                categoryService.saveCategory(category);
            } catch (DataIntegrityViolationException ignored) {
            }
        }

        for (Product product : products) {
            try {
                productService.saveProduct(product);
            } catch (DataIntegrityViolationException ignored) {
            }
        }
    }
}
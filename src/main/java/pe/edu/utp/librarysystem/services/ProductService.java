package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product) {
        Product productToUpdate = productRepository.findById(id).orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setName(product.getName());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setStock(product.getStock());
            productToUpdate.setUnitPrice(product.getUnitPrice());
            productRepository.save(productToUpdate);
        }
    }

    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> searchProducts(String name, String categoryName) {
        return productRepository.findByNameContainingIgnoreCaseAndCategoryNameContainingIgnoreCaseAndActiveTrue(name, categoryName);
    }

    public List<Product> listProducts() {
        return productRepository.findByActiveTrue();
    }

    public void deleteProduct(Long productoId) throws Exception {
        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new Exception("Producto no encontrado"));
        producto.setActive(false);
        productRepository.save(producto);
    }
}

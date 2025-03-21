package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.entities.Sale;
import pe.edu.utp.librarysystem.entities.SaleDetail;
import pe.edu.utp.librarysystem.repository.ProductRepository;
import pe.edu.utp.librarysystem.repository.SaleDetailRepository;
import pe.edu.utp.librarysystem.repository.SaleRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleDetailRepository saleDetailRepository;
    @Autowired
    private ProductService productService;

    @Transactional
    public void saveSale(Sale sale, List<Long> productIds, List<Integer> amounts) {
        // Calcular el total de la venta
        BigDecimal totalSale = calculateTotalSale(productIds, amounts);

        // Asignar el total calculado a la venta
        sale.setTotalSale(totalSale);

        // Guardar la venta
        saleRepository.save(sale);

        // Guardar los detalles de la venta
        if (productIds != null && !productIds.isEmpty()) {
            for (int i = 0; i < productIds.size(); i++) {
                Long productId = productIds.get(i);
                Integer amount = amounts.get(i);
                Product product = productService.findById(productId);

                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setSale(sale);
                saleDetail.setProduct(product);
                saleDetail.setAmount(amount);
                saleDetail.setUnitPrice(product.getUnitPrice()); // Aquí obtienes el precio unitario del producto

                saleDetailRepository.save(saleDetail);
            }
        }
    }

    // Método para calcular el total de la venta
    private BigDecimal calculateTotalSale(List<Long> productIds, List<Integer> amounts) {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer amount = amounts.get(i);
            Product product = productService.findById(productId);
            BigDecimal unitPrice = product.getUnitPrice(); // Obtener el precio unitario del producto
            BigDecimal amountBigDecimal = new BigDecimal(amount); // Convertir la cantidad a BigDecimal

            // Calcular el subtotal para este producto y sumarlo al total
            BigDecimal subtotal = unitPrice.multiply(amountBigDecimal);
            total = total.add(subtotal);
        }
        return total;
    }

    public List<Sale> listSales() {
        return saleRepository.findAll();
    }

    @Transactional
    public void updateSale(Sale updatedSale, List<Long> productIds, List<Integer> amounts) {
        Sale sale = findById(updatedSale.getId());

        sale.setClient(updatedSale.getClient());
        sale.setEmployee(updatedSale.getEmployee());
        sale.setSaleDate(updatedSale.getSaleDate());
        sale.setTotalSale(updatedSale.getTotalSale());

        // Eliminar los detalles de venta existentes para esta venta
        saleDetailRepository.deleteById(sale.getId());

        // Guardar los nuevos detalles de venta actualizados
        if (productIds != null && !productIds.isEmpty()) {
            for (int i = 0; i < productIds.size(); i++) {
                Long productId = productIds.get(i);
                Integer amount = amounts.get(i);
                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setSale(sale);
                saleDetail.setProduct(productService.findById(productId));
                saleDetail.setAmount(amount);
                saleDetail.setUnitPrice(productService.findById(productId).getUnitPrice());
                saleDetailRepository.save(saleDetail);
            }
        }

        saleRepository.save(sale);
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).get();
    }

    @Transactional
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}

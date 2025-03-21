package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.librarysystem.entities.Product;
import pe.edu.utp.librarysystem.entities.Quote;
import pe.edu.utp.librarysystem.entities.QuoteDetail;
import pe.edu.utp.librarysystem.entities.Sale;
import pe.edu.utp.librarysystem.repository.QuoteDetailRepository;
import pe.edu.utp.librarysystem.repository.QuoteRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteDetailRepository quoteDetailRepository;
    
    @Autowired
    private ProductService productService;

    @Transactional
    public void saveQuote(Quote quote, List<Long> productIds, List<Integer> quantities) {
        // Calcular el total de la cotización
        BigDecimal totalQuote = calculateTotalQuote(productIds, quantities);

        // Asignar el total calculado a la cotización
        quote.setTotalQuote(totalQuote);

        // Guardar la cotización
        quoteRepository.save(quote);

        // Guardar los detalles de la cotización
        if (productIds != null && !productIds.isEmpty()) {
            for (int i = 0; i < productIds.size(); i++) {
                Long productId = productIds.get(i);
                Integer quantity = quantities.get(i);
                Product product = productService.findById(productId);

                QuoteDetail quoteDetail = new QuoteDetail();
                quoteDetail.setQuote(quote);
                quoteDetail.setProduct(product);
                quoteDetail.setQuantity(quantity);
                quoteDetail.setUnitPrice(product.getUnitPrice());

                quoteDetailRepository.save(quoteDetail);
            }
        }
    }

    public List<Quote> searchQuotes(String clientName, String employeeName) {
        return quoteRepository.findByClientFullNameContainingIgnoreCaseAndEmployeeFullNameContainingIgnoreCase(
                clientName != null ? clientName : "",
                employeeName != null ? employeeName : ""
        );
    }

    // Método para calcular el total de la cotización
    public BigDecimal calculateTotalQuote(List<Long> productIds, List<Integer> quantities) {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer quantity = quantities.get(i);
            Product product = productService.findById(productId);
            BigDecimal unitPrice = product.getUnitPrice();
            BigDecimal quantityBigDecimal = new BigDecimal(quantity);

            // Calcular el subtotal para este producto y sumarlo al total
            BigDecimal subtotal = unitPrice.multiply(quantityBigDecimal);
            total = total.add(subtotal);
        }
        return total;
    }

    public List<Quote> listQuotes() {
        return quoteRepository.findAll();
    }

    @Transactional
    public void updateQuote(Quote updatedQuote, List<Long> productIds, List<Integer> quantities) {
        // Calcular el total de la cotización
        BigDecimal totalQuote = calculateTotalQuote(productIds, quantities);
        updatedQuote.setTotalQuote(totalQuote);

        // Obtener la cotización existente por su ID
        Quote quote = quoteRepository.findById(updatedQuote.getId())
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con ID: " + updatedQuote.getId()));

        // Actualizar los datos generales de la cotización
        quote.setClient(updatedQuote.getClient());
        quote.setEmployee(updatedQuote.getEmployee());
        quote.setQuoteDate(updatedQuote.getQuoteDate());
        quote.setTotalQuote(updatedQuote.getTotalQuote());

        // Eliminar los detalles de cotización existentes para esta cotización
        quoteDetailRepository.deleteByQuote(quote);

        // Guardar los nuevos detalles de cotización actualizados
        if (productIds != null && !productIds.isEmpty()) {
            for (int i = 0; i < productIds.size(); i++) {
                Long productId = productIds.get(i);
                Integer quantity = quantities.get(i);
                Product product = productService.findById(productId);

                QuoteDetail quoteDetail = new QuoteDetail();
                quoteDetail.setQuote(quote);
                quoteDetail.setProduct(product);
                quoteDetail.setQuantity(quantity);
                quoteDetail.setUnitPrice(product.getUnitPrice());
                quoteDetailRepository.save(quoteDetail);
            }
        }

        // Guardar la cotización actualizada
        quoteRepository.save(quote);
    }

    public Quote findById(Long id) {
        return quoteRepository.findById(id).get();
    }

    @Transactional
    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
    }
}

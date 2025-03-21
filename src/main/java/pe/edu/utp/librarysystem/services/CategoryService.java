package pe.edu.utp.librarysystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.librarysystem.entities.Category;
import pe.edu.utp.librarysystem.entities.Client;
import pe.edu.utp.librarysystem.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> listCategories() {
        return categoryRepository.findByActiveTrue();
    }

    public void updateCategory(Long id, Category category) {
        Category categoryToUpdate = categoryRepository.findById(id).orElse(null);
        if (categoryToUpdate != null) {
            categoryToUpdate.setName(category.getName());
            categoryToUpdate.setDescription(category.getDescription());
            categoryRepository.save(categoryToUpdate);
        }
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Categoría no encontrada"));
        category.setActive(false);
        categoryRepository.save(category);
    }

    public List<Category> searchCategories(String name) {
        return categoryRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }
}

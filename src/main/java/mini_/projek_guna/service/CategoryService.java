package mini_.projek_guna.service;

import mini_.projek_guna.Exception.ValidasiException;
import mini_.projek_guna.Repository.CategoryRepository;
import mini_.projek_guna.model.Category;
import mini_.projek_guna.request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private  final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public Category tambahcategory(CategoryRequest request) {
        categoryRepository.findByName(request.getName())
                .ifPresent(existing -> {
                    throw new ValidasiException("Category sudah terdaftar");
                });

        Category category = new Category();
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    public List<Category> semuaCategory(){
        return categoryRepository.findAll();
    }

    public Category cariCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Category tidak ditemukan"));
    }

    public Category updateCategory(Long id, CategoryRequest dataBaru){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ValidasiException("Category tidak ditemukan"));

        categoryRepository.findByName(dataBaru.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ValidasiException("Category sudah terdaftar");
                });

        category.setName(dataBaru.getName());
        return categoryRepository.save(category);
    }
}

package mini_.projek_guna.controller;

import jakarta.validation.Valid;
import mini_.projek_guna.model.Category;
import mini_.projek_guna.request.CategoryRequest;
import mini_.projek_guna.response.WebResponse;
import mini_.projek_guna.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService  categoryService){
        this.categoryService=categoryService;
    }

    @PostMapping
    public WebResponse<Category> tambahCategory(@Valid @RequestBody CategoryRequest request){
        return  WebResponse.<Category>builder()
                .status("Success")
                .message("Category berhasil ditambahkan")
                .data(categoryService.tambahcategory(request))
                .build();
    }

    @GetMapping
    public WebResponse<List<Category>> semuaCategory(){
        return WebResponse.<List<Category>>builder()
                .status("Success")
                .message("Semua data category berhasil diambil")
                .data(categoryService.semuaCategory())
                .build();
    }

    @GetMapping("/{id}")
    public WebResponse<Category> cariCategoryById(@PathVariable Long id) {
        return WebResponse.<Category>builder()
                .status("Success")
                .message("Detail category berhasil diambil")
                .data(categoryService.cariCategoryById(id))
                .build();
    }

    @PutMapping("/{id}")
    public WebResponse<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request){
        return WebResponse.<Category>builder()
                .status("Success")
                .message("Category berhasil diupdate")
                .data(categoryService.updateCategory(id, request))
                .build();
    }
}

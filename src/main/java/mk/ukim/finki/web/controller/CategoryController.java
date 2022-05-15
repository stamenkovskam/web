package mk.ukim.finki.web.controller;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoryPage(Model model){
        model.addAttribute("categories", this.categoryService.listCategories());
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }
    @GetMapping("/add-category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCategoryPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-category");
        return "master-template";
    }
    @PostMapping("/add")
    public String saveCategory(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description) {
        if (id != null) {
            this.categoryService.update(name, description);
        } else {
            this.categoryService.create(name, description);
        }
        return "redirect:/categories";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        return "redirect:/categories";
    }

}

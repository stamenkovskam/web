package mk.ukim.finki.web.controller;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.KidsProduct;
import mk.ukim.finki.service.CategoryService;
import mk.ukim.finki.service.KidsProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kid_products")
public class KidsProductController {

    private final KidsProductService kidsProductService;
    private final CategoryService categoryService;

    public KidsProductController(
            KidsProductService kidsProductService, CategoryService categoryService) {
        this.kidsProductService = kidsProductService;

        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String nameSearch,
                                 @RequestParam(required = false)Long categoryId,
                                 @RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<KidsProduct> products;
        List<Category> categories = this.categoryService.listCategories();

        if (nameSearch == null && categoryId == null) {
            products = this.kidsProductService.findAll();
        } else {
            products = this.kidsProductService.listKidsProductsByNameAndCategory(nameSearch, categoryId);
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "kids_products");
        return "master-template";
    }



    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.kidsProductService.deleteById(id);
        return "redirect:/kid_products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.kidsProductService.findById(id).isPresent()) {
            KidsProduct product = this.kidsProductService.findById(id).get();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-kid_product");
            return "master-template";
        }
        return "redirect:/kid_products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-kid_product");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam Long category,
            String photo) {
        if (id != null) {
            this.kidsProductService.edit(id, name, price, quantity, category,photo);
        } else {
            this.kidsProductService.save(name, price, quantity, category,photo);
        }
        return "redirect:/kid_products";
    }



}

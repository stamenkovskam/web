package mk.ukim.finki.web.controller;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.WomenProduct;
import mk.ukim.finki.service.CategoryService;
import mk.ukim.finki.service.WomenProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/women_products")
public class WomenProductController {

    private final WomenProductService womenProductService;
    private final CategoryService categoryService;

    public WomenProductController(
            WomenProductService womenProductService, CategoryService categoryService) {
        this.womenProductService = womenProductService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String nameSearch,
                                 @RequestParam(required = false)Long categoryId,
                                 @RequestParam(required = false) String error, Model model) {

        List<WomenProduct> products;
        List<Category> categories = this.categoryService.listCategories();

        if (nameSearch == null && categoryId == null) {
            products = this.womenProductService.findAll();
        } else {
            products = this.womenProductService.listProductsByNameAndCategory(nameSearch, categoryId);
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "women_products");
        return "master-template";
    }



    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.womenProductService.deleteById(id);
        return "redirect:/women_products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.womenProductService.findById(id).isPresent()) {
            WomenProduct product = this.womenProductService.findById(id).get();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-women_product");
            return "master-template";
        }
        return "redirect:/women_products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-women_product");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam Long category, String photo) {
        if (id != null) {
            this.womenProductService.edit(id, name, price, quantity, category,photo);
        } else {
            this.womenProductService.save(name, price, quantity, category,photo);
        }
        return "redirect:/women_products";
    }



}

package mk.ukim.finki.web.controller;

import mk.ukim.finki.model.MenProduct;
import mk.ukim.finki.service.MenProductService;
import mk.ukim.finki.model.Category;
import mk.ukim.finki.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/men_products")
public class MenProductController {

    private final MenProductService productService;
    private final CategoryService categoryService;

    public MenProductController(MenProductService productService,
                                CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String nameSearch,
                                 @RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false) String error, Model model) {

        List<MenProduct> products;
        List<Category> categories = this.categoryService.listCategories();

        if (nameSearch == null && categoryId == null) {
            products = this.productService.findAll();
        } else {
            products = this.productService.listProductsByNameAndCategory(nameSearch, categoryId);
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "men_products");
        return "master-template";
    }



    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/men_products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.productService.findById(id).isPresent()) {
            MenProduct product = this.productService.findById(id).get();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "add-men_product");
            return "master-template";
        }
        return "redirect:/men_products?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-men_product");
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
            this.productService.edit(id, name, price, quantity, category,photo);
        } else {
            this.productService.save(name, price, quantity, category,photo);
        }
        return "redirect:/men_products";
    }



}

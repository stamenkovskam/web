package mk.ukim.finki.web.controller;

import mk.ukim.finki.model.ShoppingCart;
import mk.ukim.finki.model.User;
import mk.ukim.finki.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("products", this.shoppingCartService.listAllMenProductsInShoppingCart(shoppingCart.getId()));
         model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";
    }

    @PostMapping("/add-men_product/{id}")
    public String addMenProductToShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addMenProductToShoppingCart(user.getUsername(), id);
            return "redirect:/men_products";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/add-women_product/{id}")
    public String addWomenProductToShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addWomenProductToShoppingCart(user.getUsername(), id);
            return "redirect:/women_products";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/add-kid_product/{id}")
    public String addKidProductToShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addKidProductToShoppingCart(user.getUsername(), id);
            return "redirect:/kid_products";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @GetMapping("/pay")
    public String getSPay(
                          @RequestParam(required = false) String error,
                          HttpServletRequest req,
                                      Model model,Authentication authentication) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        this.shoppingCartService.delete();
        model.addAttribute("bodyContent", "SPay");
        return "master-template";
    }


}

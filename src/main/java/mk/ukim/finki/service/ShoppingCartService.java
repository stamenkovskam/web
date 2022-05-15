package mk.ukim.finki.service;

import mk.ukim.finki.model.KidsProduct;
import mk.ukim.finki.model.MenProduct;
import mk.ukim.finki.model.ShoppingCart;
import mk.ukim.finki.model.WomenProduct;

import java.util.List;

public interface ShoppingCartService {


    List<MenProduct> listAllMenProductsInShoppingCart(Long cartId);

    List<WomenProduct> listAllWomenProductsInShoppingCart(Long cartId);

    List<KidsProduct> listAllKidProductsInShoppingCart(Long cartId);

    ShoppingCart getActiveShoppingCart(String username);

    ShoppingCart addMenProductToShoppingCart(String username, Long productId);
    ShoppingCart addWomenProductToShoppingCart(String username, Long productId);
    ShoppingCart addKidProductToShoppingCart(String username, Long productId);

    void delete();
}

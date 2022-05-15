package mk.ukim.finki.service.impl;

import mk.ukim.finki.model.*;
import mk.ukim.finki.service.KidsProductService;
import mk.ukim.finki.service.MenProductService;
import mk.ukim.finki.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.model.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.model.exceptions.UserNotFoundException;
import mk.ukim.finki.repository.ShoppingCartRepository;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.service.ShoppingCartService;
import mk.ukim.finki.service.WomenProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final MenProductService menProductService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository, MenProductService menProductService, WomenProductService womenProductService, KidsProductService kidsProductService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.menProductService = menProductService;
    }

    @Override
    public List<MenProduct> listAllMenProductsInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);

        return this.shoppingCartRepository.findById(cartId).get().getMenProducts();
    }

    @Override
    public List<WomenProduct> listAllWomenProductsInShoppingCart(Long cartId) {
        return null;
//        if(!this.shoppingCartRepository.findById(cartId).isPresent())
//            throw new ShoppingCartNotFoundException(cartId);
//
//        return this.shoppingCartRepository.findById(cartId).get().getWomenProducts();
    }

    @Override
    public List<KidsProduct> listAllKidProductsInShoppingCart(Long cartId) {
        return null;
//        if(!this.shoppingCartRepository.findById(cartId).isPresent())
//            throw new ShoppingCartNotFoundException(cartId);
//
//        return this.shoppingCartRepository.findById(cartId).get().getKidsProducts();
    }


    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addMenProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        MenProduct product = this.menProductService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart.getMenProducts()
                .stream().filter(i -> i.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getMenProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
    @Override
    public ShoppingCart addWomenProductToShoppingCart(String username, Long productId) {
//        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
//        WomenProduct product = this.womenProductService.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException(productId));
        return null;
//        if(shoppingCart.getWomenProducts()
//                .stream().filter(i -> i.getId().equals(productId))
//                .collect(Collectors.toList()).size() > 0)
//            throw new ProductAlreadyInShoppingCartException(productId, username);
//        shoppingCart.getWomenProducts().add(product);
//        return this.shoppingCartRepository.save(shoppingCart);
    }
    @Override
    public ShoppingCart addKidProductToShoppingCart(String username, Long productId) {
//        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
//        KidsProduct product = this.kidsProductService.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException(productId));
        return null;
//        if(shoppingCart.getKidsProducts()
//                .stream().filter(i -> i.getId().equals(productId))
//                .collect(Collectors.toList()).size() > 0)
//            throw new ProductAlreadyInShoppingCartException(productId, username);
//        shoppingCart.getKidsProducts().add(product);
//        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void delete() {
        this.shoppingCartRepository.deleteAll();
    }

}

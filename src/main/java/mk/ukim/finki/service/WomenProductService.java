package mk.ukim.finki.service;

import mk.ukim.finki.model.WomenProduct;
import mk.ukim.finki.model.dto.WomenProductDto;

import java.util.List;
import java.util.Optional;

public interface WomenProductService {

    List<WomenProduct> findAll();

    Optional<WomenProduct> findById(Long id);

    Optional<WomenProduct> findByName(String name);

    Optional<WomenProduct> save(String name, Double price, Integer quantity, Long category, String photo);

    Optional<WomenProduct> save(WomenProductDto productDto);

    Optional<WomenProduct> edit(Long id, String name, Double price, Integer quantity, Long category, String photo);

    Optional<WomenProduct> edit(Long id, WomenProductDto productDto);

    void deleteById(Long id);

    List<WomenProduct> listProductsByNameAndCategory(String name, Long categoryId);

}

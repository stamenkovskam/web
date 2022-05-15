package mk.ukim.finki.service;

import mk.ukim.finki.model.KidsProduct;
import mk.ukim.finki.model.dto.KidProductDto;

import java.util.List;
import java.util.Optional;

public interface KidsProductService {
    List<KidsProduct> findAll();

    Optional<KidsProduct> findById(Long id);

    Optional<KidsProduct> findByName(String name);

    Optional<KidsProduct> save(String name, Double price, Integer quantity, Long category, String photo);


    Optional<KidsProduct> save(KidProductDto kidProductDto);

    Optional<KidsProduct> edit(Long id, String name, Double price, Integer quantity, Long category, String photo);

    Optional<KidsProduct> edit(Long id, KidProductDto productDto);

    void deleteById(Long id);

    List<KidsProduct> listKidsProductsByNameAndCategory(String name, Long categoryId);
}

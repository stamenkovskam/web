package mk.ukim.finki.service;

import mk.ukim.finki.model.MenProduct;
import mk.ukim.finki.model.dto.MenProductDto;

import java.util.List;
import java.util.Optional;

public interface MenProductService {

    List<MenProduct> findAll();



    Optional<MenProduct> findById(Long id);

    Optional<MenProduct> findByName(String name);

    Optional<MenProduct> save(String name, Double price, Integer quantity, Long category, String photo);

    Optional<MenProduct> save(MenProductDto productDto);

    Optional<MenProduct> edit(Long id, String name, Double price, Integer quantity, Long category, String photo);

    Optional<MenProduct> edit(Long id, MenProductDto productDto);

    void deleteById(Long id);

    List<MenProduct> listProductsByNameAndCategory(String name, Long categoryId);

}

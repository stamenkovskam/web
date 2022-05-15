package mk.ukim.finki.repository;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.WomenProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WomenProductRepository extends JpaRepository<WomenProduct, Long> {
    Optional<WomenProduct> findByName(String name);
    void deleteByName(String name);

    List<WomenProduct> findAllByNameLikeAndCategoryContaining(String name, Category category);
    List<WomenProduct> findAllByNameLike(String name);
    List<WomenProduct> findAllByCategoryContaining(Category category);

}

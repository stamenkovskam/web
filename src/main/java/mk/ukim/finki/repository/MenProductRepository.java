package mk.ukim.finki.repository;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.MenProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenProductRepository extends JpaRepository<MenProduct, Long> {
    Optional<MenProduct> findByName(String name);
    void deleteByName(String name);

    List<MenProduct> findAllByNameLikeAndCategoryContaining(String name, Category category);
    List<MenProduct> findAllByNameLike(String name);
    List<MenProduct> findAllByCategoryContaining(Category category);

}

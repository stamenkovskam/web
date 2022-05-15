package mk.ukim.finki.repository;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.KidsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface KidsProductRepository extends JpaRepository<KidsProduct,Long> {
    Optional<KidsProduct> findByName(String name);
    void deleteByName(String name);

    List<KidsProduct> findAllByNameLikeAndCategoryContaining(String name, Category category);
    List<KidsProduct> findAllByNameLike(String name);
    List<KidsProduct> findAllByCategoryContaining(Category category);
}

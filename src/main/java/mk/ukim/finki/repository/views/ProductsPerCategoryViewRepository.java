package mk.ukim.finki.repository.views;

import mk.ukim.finki.model.views.ProductsPerCategoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsPerCategoryViewRepository
        extends JpaRepository<ProductsPerCategoryView, Long> {
}

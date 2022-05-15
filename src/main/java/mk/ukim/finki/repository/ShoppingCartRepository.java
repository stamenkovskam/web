package mk.ukim.finki.repository;

import mk.ukim.finki.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.model.ShoppingCart;
import mk.ukim.finki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}

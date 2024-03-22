package hcmute.web_cosmetic.client.cart;

import hcmute.web_cosmetic.entity.cart.Cart;
import hcmute.web_cosmetic.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

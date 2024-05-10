package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

        Cart findByCustomerId(Long userId);
}

package banana_cosmetic.client.order;
import banana_cosmetic.common.entity.User;
import banana_cosmetic.common.entity.cart.Cart;
import banana_cosmetic.common.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User customer);
}

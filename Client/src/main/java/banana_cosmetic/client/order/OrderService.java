package banana_cosmetic.client.order;

import banana_cosmetic.client.cart.CartRepository;
import banana_cosmetic.common.entity.User;
import banana_cosmetic.common.entity.cart.Cart;
import banana_cosmetic.common.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public void save(Order order) {
        repository.save(order);
    }

    public Order getOrder(Long id) {

        return repository.findById(id).orElse(null);
    }

    public List<Order> findAllOrdersByCustomer(User customer) {
        return repository.findByCustomer(customer);
    }
}

package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.cart.Cart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;
    @Autowired
    private ModelMapper mapper;

    public Cart get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Cart cart) {
        repository.save(cart);
    }
}

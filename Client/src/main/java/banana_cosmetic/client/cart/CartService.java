package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import banana_cosmetic.common.entity.cart.LineItem;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;


    public void addLineItemToCart(LineItem lineitem, Long id) {
        Cart cart = getCart(id);
        cart.addLineItem(lineitem);
        repository.save(cart);
    }

    public Cart getCart(Long id) {
        return repository.findByCustomerId(id);
    }
    public void deleteFromCart(List<Long> lineItemIds, Long id) {
        Cart cart = getCart(id);
        cart.removeLineItemsByIds(lineItemIds);
        repository.save(cart);
    }

    public void updateQuantityInCart(Long lineItemId, int quantity, Long id) {
        Cart cart = getCart(id);
        cart.updateLineItemQuantity(lineItemId, quantity);
        repository.save(cart);
    }


}






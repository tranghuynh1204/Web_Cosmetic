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


    public void addLineItemToCart(LineItem lineitem) {
        Cart cart = getCart();
        cart.addLineItem(lineitem);
        repository.save(cart);
    }

    public Cart getCart() {
        // Lấy ID người dùng hiện tại.
        Long userId = 2L; // chưa viết
        return repository.findByCustomerId(userId);
    }
    public void deleteFromCart(List<Long> lineItemIds) {
        Cart cart = getCart();
        cart.removeLineItemsByIds(lineItemIds);
        repository.save(cart);
    }

    public void updateQuantityInCart(Long lineItemId, int quantity) {
        Cart cart = getCart();
        cart.updateLineItemQuantity(lineItemId, quantity);
        repository.save(cart);
    }


}






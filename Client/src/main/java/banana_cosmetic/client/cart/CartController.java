package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("")
    public Cart getCart(){
        Cart cart = service.get(1L);
        return cart;
    }

    @PostMapping("update")
    public void updateCart(@RequestBody Cart cart){
        service.save(cart);
    }
}

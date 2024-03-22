package hcmute.web_cosmetic.client.cart;

import hcmute.web_cosmetic.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

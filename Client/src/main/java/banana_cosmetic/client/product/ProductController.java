package banana_cosmetic.client.product;

import banana_cosmetic.client.cart.CartService;
import banana_cosmetic.common.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private CartService service;

    @GetMapping("")
    public String viewProduct(){

//        ProductLine productLine = service.get(nameProductLine);
//        String[] classifications = productLine.getClassifications().split("-");
//        String[][] keys = productLine.getProducts().keySet().stream()
//                .map(key -> key.split("-"))
//                .toArray(String[][]::new);
//        model.addAttribute("productLine",productLine);
//        model.addAttribute("products",productLine.getProducts());
//        model.addAttribute("classifications",classifications);
//        model.addAttribute("keys",keys);

        Cart cart = new Cart();
        service.save(cart);
        return "product";
    }
}

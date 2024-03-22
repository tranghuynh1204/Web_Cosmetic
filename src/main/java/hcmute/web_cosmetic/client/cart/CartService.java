package hcmute.web_cosmetic.client.cart;

import hcmute.web_cosmetic.entity.cart.Cart;
import hcmute.web_cosmetic.entity.product.ProductLine;
import hcmute.web_cosmetic.entity.product.ProductLineDto;
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
        return repository.findById(id)
                .map(cart -> {
                    cart.getItems().forEach(item -> {
                        ProductLine productLine = item.getProduct().getProductLine();
                        item.setProductLine(mapper.map(productLine, ProductLineDto.class));
                    });
                    return cart;
                })
                .orElse(null);
    }

    public void save(Cart cart) {
        repository.save(cart);
    }
}

package banana_cosmetic.common.entity.cart;

import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class Cart extends IdBasedEntity {

    @OneToOne
    private User customer;
    @JoinColumn(name = "cart_id")
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<LineItem> items;

    public Cart() {}

    public Cart(User customer) {
        this.customer = customer;
        items = new ArrayList<>();
    }


}

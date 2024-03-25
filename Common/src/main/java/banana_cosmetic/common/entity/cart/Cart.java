package banana_cosmetic.common.entity.cart;

import banana_cosmetic.common.entity.Customer;
import banana_cosmetic.common.entity.IdBasedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class Cart extends IdBasedEntity {

    @OneToOne
    private Customer customer;
    @JoinColumn(name = "cart_id")
    @OneToMany
    private List<LineItem> items;

    public Cart() {}

    public Cart(Customer customer) {
        this.customer = customer;
        items = new ArrayList<>();
    }


}

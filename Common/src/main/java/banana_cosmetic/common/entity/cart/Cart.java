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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineItem> items;

    public Cart() {
    }

    public Cart(User customer) {
        this.customer = customer;
        items = new ArrayList<>();
    }

    public void addLineItem(LineItem lineitem) {

        for (LineItem existingLineItem : items) {
            if (existingLineItem.getProduct().getId().equals(lineitem.getProduct().getId())) {
                existingLineItem.setQuantity(existingLineItem.getQuantity() + lineitem.getQuantity());
                return;
            }
        }
        items.add(lineitem);
    }

    public void removeLineItemsByIds(List<Long> lineItemIds) {
        this.getItems().removeIf(item -> lineItemIds.contains(item.getId()));
    }

    public void updateLineItemQuantity(Long lineItemId, int quantity) {
        for (LineItem item : items) {
            if (item.getId().equals(lineItemId)) {
                item.setQuantity(quantity);
                break;
            }
        }
    }

}

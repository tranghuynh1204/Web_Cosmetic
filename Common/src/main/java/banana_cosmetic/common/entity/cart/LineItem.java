package banana_cosmetic.common.entity.cart;

import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.entity.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LineItem extends IdBasedEntity {

    private int quantity;
    @ManyToOne
    private Product product;
//    @Transient
//    private ProductWithClassificationsDto productLine;

    public LineItem() {}

    public LineItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

}

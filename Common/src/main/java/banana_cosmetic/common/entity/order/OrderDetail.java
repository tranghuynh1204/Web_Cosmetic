package banana_cosmetic.common.entity.order;

import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.entity.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetail extends IdBasedEntity {

    private int quantity;
    private Long price;
    @ManyToOne
    private Product product;
}

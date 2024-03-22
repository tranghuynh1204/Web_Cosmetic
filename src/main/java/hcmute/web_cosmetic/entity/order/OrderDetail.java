package hcmute.web_cosmetic.entity.order;

import hcmute.web_cosmetic.entity.IdBasedEntity;
import hcmute.web_cosmetic.entity.product.Product;
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

package hcmute.web_cosmetic.entity.cart;

import hcmute.web_cosmetic.entity.IdBasedEntity;
import hcmute.web_cosmetic.entity.product.Product;
import hcmute.web_cosmetic.entity.product.ProductLine;
import hcmute.web_cosmetic.entity.product.ProductLineDto;
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
    @Transient
    private ProductLineDto productLine;

    public LineItem() {}

    public LineItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

}

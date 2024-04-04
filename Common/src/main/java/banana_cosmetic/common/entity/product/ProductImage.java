package banana_cosmetic.common.entity.product;


import banana_cosmetic.common.entity.IdBasedEntity;
import jakarta.persistence.Entity;

@Entity
public class ProductImage extends IdBasedEntity {

    private String name;
    public ProductImage() {}

}



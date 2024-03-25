package banana_cosmetic.common.entity.product;

import banana_cosmetic.common.entity.IdBasedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Product extends IdBasedEntity {

    private Long price;
    private Long salePrice;
    @JoinColumn(name = "product_id")
    @OneToMany
    private List<ProductImage> images;
    @ManyToOne
    @JsonIgnore
    private ProductLine productLine ;

    public Product() {}
}

package banana_cosmetic.common.entity.product;

import banana_cosmetic.common.entity.IdBasedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
public class Product extends IdBasedEntity {

    private Long price;
    private Long salePrice;
    @JoinColumn(name = "product_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;
    @ManyToOne
    @JsonIgnore
    private ProductLine productLine;

    public void updateImages(List<ProductImage> images) {
        this.images.clear();
        this.images.addAll(images);

    }
}

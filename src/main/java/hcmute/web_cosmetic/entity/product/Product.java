package hcmute.web_cosmetic.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hcmute.web_cosmetic.entity.IdBasedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
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

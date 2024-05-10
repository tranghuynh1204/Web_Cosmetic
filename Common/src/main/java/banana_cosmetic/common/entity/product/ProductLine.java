package banana_cosmetic.common.entity.product;

import banana_cosmetic.common.util.GachaUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "product_line",
        indexes = {@Index(name = "idx_name", columnList = "name", unique = true)})
public class ProductLine extends IdBasedEntity {

    @Column(unique = true)
    private String name;
    private String origin;
    private String classifications;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    @JoinColumn(name = "product_line_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, Product> products;

    public void updateProducts(Map<String, Product> products) {
        this.products.clear();
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            String productId = entry.getKey();
            Product product = entry.getValue();
            this.products.put(productId, product);
        }
    }

    public String getImage() {
        if (id == null) {
            return null;
        }
        return "https://res.cloudinary.com/bananacosmetic/image/upload/productline_" + id + "?" + GachaUtil.gachaNumber();
    }


    public ProductLine() {
    }
}

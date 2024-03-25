package banana_cosmetic.common.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@Entity
@Table(name = "product_line",
        indexes = {@Index(name = "idx_name",  columnList="name", unique = true)})
public class ProductLine extends IdBasedEntity {

    private String name;
    private String origin;
    private String classifications;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    @JsonIgnore
    @JoinColumn(name = "product_line_id")
    @OneToMany
    private Map<String, Product> products;

    public ProductLine() {}
}

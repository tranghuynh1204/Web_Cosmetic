package banana_cosmetic.admin.product;

import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.category.Category;
import banana_cosmetic.common.entity.product.Product;
import banana_cosmetic.common.util.GachaUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ProductLineDto {

    private Long id;
    private String name;
    private String brandName;
    private String categoryName;

    public String getImage() {
        return "https://res.cloudinary.com/bananacosmetic/image/upload/productline_" + id + "?" + GachaUtil.gachaNumber();
    }
}

package banana_cosmetic.admin.product;

import banana_cosmetic.common.util.GachaUtil;
import lombok.Getter;
import lombok.Setter;

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

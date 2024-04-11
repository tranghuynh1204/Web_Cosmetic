package banana_cosmetic.common.entity.brand;

import banana_cosmetic.common.util.GachaUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDto {
    private Long id;
    private String name;

    public String getLogo() {
        return "https://res.cloudinary.com/bananacosmetic/image/upload/brand_" + id + "?" + GachaUtil.gachaNumber();
    }
}

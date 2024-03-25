package banana_cosmetic.common.entity.brand;

import banana_cosmetic.common.entity.IdBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import banana_cosmetic.common.util.GachaUtil;
@Entity
@Getter
@Setter
public class Brand extends IdBasedEntity {

    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Brand() {
    }

    public String getLogo() {
        return "https://res.cloudinary.com/bananacosmetic/image/upload/brand_" + id + "?" + GachaUtil.gachaNumber();
    }
}

package banana_cosmetic.common.entity.brand;

import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.util.GachaUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Brand extends IdBasedEntity {

    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    public String getLogo() {
        if (id == null) {
            return null;
        }
        return "https://res.cloudinary.com/bananacosmetic/image/upload/brand_" + id + "?" + GachaUtil.gachaNumber();
    }

    public Brand() {
    }

}

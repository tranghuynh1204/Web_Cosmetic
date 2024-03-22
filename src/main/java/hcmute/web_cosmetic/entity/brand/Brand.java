package hcmute.web_cosmetic.entity.brand;

import hcmute.web_cosmetic.Util.GachaUtil;
import hcmute.web_cosmetic.entity.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

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
        return "https://res.cloudinary.com/drncbsuo5/image/upload/brand_" + id+"?"+ GachaUtil.gachaNumber();
    }
}

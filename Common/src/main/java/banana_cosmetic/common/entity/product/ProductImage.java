package banana_cosmetic.common.entity.product;


import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.util.CloudinaryUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ProductImage extends IdBasedEntity {

    private String name;

    public ProductImage() {
    }

    @PrePersist
    public void beforeAdd() {
        String imageUrl = CloudinaryUtil.uploadImage(name);
        this.name = imageUrl.substring(imageUrl.indexOf("upload/") + 7, imageUrl.lastIndexOf("."));
    }

    @PreRemove
    public void beforeDelete() {
        CloudinaryUtil.deleteImage(name.substring(name.indexOf("/") + 1));
    }

    public String getLink() {
        return "https://res.cloudinary.com/bananacosmetic/image/upload/" + name + ".png";
    }
}



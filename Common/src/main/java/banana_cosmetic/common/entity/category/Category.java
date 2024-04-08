package banana_cosmetic.common.entity.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import banana_cosmetic.common.entity.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends IdBasedEntity implements Cloneable {

    private String name;
    private String allParentIds;
    @ManyToOne
    private Category parent;
    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<Category> children;

    public Category() {
    }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
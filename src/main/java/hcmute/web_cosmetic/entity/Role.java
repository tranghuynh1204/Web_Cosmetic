package hcmute.web_cosmetic.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends IdBasedEntity{

    private String name;
    private String description;
}

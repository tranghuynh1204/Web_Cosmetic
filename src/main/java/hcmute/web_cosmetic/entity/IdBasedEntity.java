package hcmute.web_cosmetic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class IdBasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

}

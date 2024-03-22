package hcmute.web_cosmetic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends IdBasedEntity{

    private String name;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    private String password;

    public Customer() {}

}

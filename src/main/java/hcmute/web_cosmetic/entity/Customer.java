package hcmute.web_cosmetic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Customer extends IdBasedEntity{

    private String name;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String address;
    private String mail;
    private Date birth;
    private String password;

    public Customer() {}

}

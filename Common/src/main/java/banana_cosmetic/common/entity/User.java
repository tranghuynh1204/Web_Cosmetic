package banana_cosmetic.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@Table(name =  "\"user\"")
public class User extends IdBasedEntity {

    private String name;
    @Column(unique = true)
    private String phone;
    private String address;
    @Column(unique = true)
    private String mail;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
    //////////

}
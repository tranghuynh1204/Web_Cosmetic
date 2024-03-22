package hcmute.web_cosmetic.entity;

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
    @Column(unique = true)
    private String email;
    private String password;
    private String photos;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
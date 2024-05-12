package banana_cosmetic.client.user;


import banana_cosmetic.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByMailAndPassword(String mail, String password);

    User findUserByMailIgnoreCase(String customerMail);
}

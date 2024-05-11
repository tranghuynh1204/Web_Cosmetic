package banana_cosmetic.client.user;

import banana_cosmetic.client.cart.CartRepository;
import banana_cosmetic.common.entity.Role;
import banana_cosmetic.common.entity.User;
import banana_cosmetic.common.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private CartRepository cartRepository;

    public void save(User user) {
        try {
            Role role = new Role();
            role.setId(2L);
            user.setRoles(List.of(role));
            Cart cart = new Cart();
            cart.setCustomer(user);
            userRepository.save(user);
            cartRepository.save(cart);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Phone number or email đã được sử dụng");
        } catch (Exception e) {
            throw e;
        }
    }



    public User findByMailAndPassword(String mail, String password) {
        return userRepository.findByMailAndPassword(mail, password);
    }
}

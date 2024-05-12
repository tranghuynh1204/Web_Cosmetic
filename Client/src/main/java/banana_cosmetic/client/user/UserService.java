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


    public void save1(User user, String customerMail) {
        try {
            // Tìm kiếm người dùng trong cơ sở dữ liệu bằng ID
            User existingUser = userRepository.findUserByMailIgnoreCase(customerMail);
            // Kiểm tra xem người dùng có tồn tại không
            if(existingUser != null) {
                // Cập nhật thông tin người dùng
                existingUser.setName(user.getName());
                existingUser.setPhone(user.getPhone());
                existingUser.setAddress(user.getAddress());
//                existingUser.setMail(user.getMail());
                // Lưu thông tin người dùng đã cập nhật vào cơ sở dữ liệu
                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Phone number or email đã được sử dụng");
        } catch (Exception e) {
            throw e;
        }
    }


    public User findByMailAndPassword(String mail, String password) {
        return userRepository.findByMailAndPassword(mail, password);
    }

    public User findByMail(String customerMail) {
        return userRepository.findUserByMailIgnoreCase(customerMail);
    }
}

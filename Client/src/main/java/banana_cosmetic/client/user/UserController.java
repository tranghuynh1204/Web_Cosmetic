package banana_cosmetic.client.user;

import banana_cosmetic.common.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> addUser(HttpServletRequest request, User user, String otp) {
        try {
            HttpSession session = request.getSession();
            String otpCheck = (String) session.getAttribute("otp");
            if (!otp.equals(otpCheck)) {
                return new ResponseEntity<>("Otp Không hợp lệ", HttpStatus.BAD_REQUEST);
            }
            service.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(HttpServletRequest request, User user) {
        User customer = service.findByMailAndPassword(user.getMail(), user.getPassword());
        if (customer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            // Người dùng không tồn tại, trả về lỗi
            return new ResponseEntity<>("Email or password incorrect", HttpStatus.BAD_REQUEST);
        }
    }

}

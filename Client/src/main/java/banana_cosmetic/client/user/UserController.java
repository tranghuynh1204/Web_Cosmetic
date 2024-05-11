package banana_cosmetic.client.user;

import banana_cosmetic.common.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    public HttpEntity<String> addUser(HttpServletRequest request, User user, String otp) {
        try {
            HttpSession session = request.getSession();
            String otpCheck = (String) session.getAttribute("otp");
            if (!otp.equals(otpCheck)){
                return new ResponseEntity<>("Otp Không hợp lệ", HttpStatus.BAD_REQUEST);
            }
                service.save(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    @PostMapping("/login")
//    @ResponseBody
//    public String login(User user) {
//        service.checkEsixt(user);
//    }

}

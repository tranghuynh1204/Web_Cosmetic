package banana_cosmetic.client.user;

import banana_cosmetic.common.entity.User;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/profile")
//    public String showProfile(Model model, HttpSession session) {
//        // Lấy thông tin khách hàng từ session
//        User customer = (User) session.getAttribute("customer");
//
//        // Kiểm tra xem người dùng đã đăng nhập hay chưa
//        if (customer != null) {
//            // Truyền thông tin khách hàng vào model để hiển thị trên trang profile
//            model.addAttribute("customer", customer);
//            return "profile/profile-index"; // Trả về trang profile
//        } else {
//            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        // Lấy thông tin khách hàng từ session
        User customer = (User) session.getAttribute("customer");

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (customer != null) {
            // Lấy id của khách hàng từ session
            String customerMail = customer.getMail();
            // Truy vấn thông tin của User từ cơ sở dữ liệu bằng id
            User userProfile = service.findByMail(customerMail);

            // Kiểm tra xem user có tồn tại hay không
            if (userProfile != null) {
                // Truyền thông tin của User vào model để hiển thị trên trang profile
                model.addAttribute("userProfile", userProfile);
                return "profile/profile-index"; // Trả về trang profile
            } else {
                // Nếu không tìm thấy thông tin của User, trả về trang lỗi hoặc thực hiện xử lý khác
                return "redirect:/error";
            }
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }
    }

    @PostMapping("/edit")
    public String editProfile(@ModelAttribute("userProfile") User user,Model model, HttpSession session) {
        // Lấy người dùng từ session
        User sessionUser = (User) session.getAttribute("customer");

        if (sessionUser != null) {
            // Thực hiện chỉnh sửa thông tin người dùng
            String customerMail = sessionUser.getMail();
            service.save1(user,customerMail);

            // Cập nhật thông tin người dùng trong session
            model.addAttribute("userProfile", user);

//            return "profile/profile-index";
            return "redirect:/user/profile";
        } else {
            // Xử lý trường hợp session không có người dùng
            // Ví dụ: quay về trang đăng nhập hoặc hiển thị thông báo lỗi
            return "redirect:/login"; // Ví dụ: chuyển hướng về trang đăng nhập
        }
    }


    @GetMapping("/test")
    public String showTestPage() {
        return "profile/test"; // Returns the name of the HTML template to display the search page
    }
}

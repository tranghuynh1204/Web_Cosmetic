package banana_cosmetic.client.mail;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/send-otp")
public class MailController {

    @Autowired
    private MailService service;

    @PostMapping("/register")
    public HttpEntity<String> sendOtpToRegister(HttpServletRequest request, String name, String mail)  {
        String subject = "Banana Cosmetic";
        String otp = generateOtp();
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        String body = createRegisterMail(name,otp);
        try {
            service.sendMail(mail, subject, body);
            return new ResponseEntity<>("Đã gửi", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Gửi không thành công vui lòng kiểm tra lại.", HttpStatus.BAD_REQUEST);
        }
    }

    private String createRepasswordrMail(String otp) {
        return "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Xác nhận OTP</title>"
                + " <style>\n" +
                "        \n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            font-size: 14px;\n" +
                "            color: #333333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "    \n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            border: 5px solid pink; \n" +
                "            border-radius: 10px; \n" +
                "        }\n" +
                "    \n" +
                "        .header {\n" +
                "            background-image: url('https://res.cloudinary.com/bananacosmetic/image/upload/v1715417105/ton5ek3ufk1xrqbox8nl.jpg');\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    \n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "    \n" +
                "       \n" +
                "        h1 {\n" +
                "            color: pink;\n" +
                "        }\n" +
                "    \n" +
                "        p {\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "    \n" +
                "        a {\n" +
                "            color: #0099ff;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>"
                + "</head>"
                + "<body style=\"font-family: Arial, sans-serif; padding: 20px;\">"
                + "  <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <!-- Thêm ảnh của web của bạn vào đây -->\n" +
                "            <img src=\"https://res.cloudinary.com/bananacosmetic/image/upload/v1715417105/ton5ek3ufk1xrqbox8nl.jpg\" alt=\"Logo của web của bạn\" width=\"200\" height=\"200\">\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <h1>Banana Cosmetic</h1>\n" +
                "            <p>Xin chào. Dưới đây là mã OTP mới để hoàn thành quá trình đặt lại mật khẩu của bạn:</p>\n" +
                "            <p><strong style=\"color: rgb(226, 119, 137);\">" + otp + "</strong></p>\n" +
                "            <p>Vui lòng nhập mã OTP này vào trang web của chúng tôi để xác nhận việc đặt lại mật khẩu.</p>\n" +
                "            <p>Nếu bạn không yêu cầu việc đặt lại mật khẩu, vui lòng liên hệ ngay với chúng tôi.</p>\n" +
                "            <p>Cảm ơn bạn đã sử dụng web của chúng tôi!</p>\n" +
                "            <p>Trân trọng,</p>\n" +
                "            <p>Banana Cosmetic Team</p>\n" +
                "        </div>\n" +
                "    </div>"
                + "</body>"
                + "</html>";
    }

    private String createRegisterMail(String name, String otp) {
        return "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Xác nhận OTP</title>"
                + " <style>\n" +
                "        \n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            font-size: 14px;\n" +
                "            color: #333333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "    \n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            border: 5px solid pink; \n" +
                "            border-radius: 10px; \n" +
                "        }\n" +
                "    \n" +
                "        .header {\n" +
                "            background-image: url('https://res.cloudinary.com/bananacosmetic/image/upload/v1715417105/ton5ek3ufk1xrqbox8nl.jpg');\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    \n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "    \n" +
                "       \n" +
                "        h1 {\n" +
                "            color: pink;\n" +
                "        }\n" +
                "    \n" +
                "        p {\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "    \n" +
                "        a {\n" +
                "            color: #0099ff;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>"
                + "</head>"
                + "<body style=\"font-family: Arial, sans-serif; padding: 20px;\">"
                + "  <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <!-- Thêm ảnh của web của bạn vào đây -->\n" +
                "            <img src=\"https://res.cloudinary.com/bananacosmetic/image/upload/v1715417105/ton5ek3ufk1xrqbox8nl.jpg\" alt=\"Logo của web của bạn\" width=\"200\" height=\"200\">\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <h1>Banana Cosmetic</h1>\n" +
                "            <p>Xin chào, " + name + "!</p>\n" +
                "            <p>Cảm ơn bạn đã đăng ký tại Banana Cosmetic. Đây là mã OTP của bạn để hoàn thành quá trình đăng ký:</p>\n" +
                "            <p><strong style=\"color: rgb(226, 119, 137);\">" + otp + "</strong></p>\n" +
                "            <p>Vui lòng nhập mã OTP này vào trang web của chúng tôi để xác nhận đăng ký của bạn.</p>\n" +
                "            <p>Cảm ơn bạn đã sử dụng web của chúng tôi!</p>\n" +
                "            <p>Trân trọng,</p>\n" +
                "            <p>Banana Cosmetic Team</p>\n" +
                "        </div>\n" +
                "    </div>"
                + "</body>"
                + "</html>";
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }
}

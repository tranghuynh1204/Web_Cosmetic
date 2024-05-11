package banana_cosmetic.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

    @GetMapping("/login")
    public String showFormLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String showHome(){
        return "home";
    }
}

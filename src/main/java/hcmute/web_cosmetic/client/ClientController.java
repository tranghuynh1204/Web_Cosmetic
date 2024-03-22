package hcmute.web_cosmetic.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class ClientController {

    @GetMapping("")
    public String viewHome(){
        return "client/home";
    }
}

package banana_cosmetic.client.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductLineService service;

    @GetMapping("")
    public String viewProduct(){

//        ProductLine productLine = service.get(nameProductLine);
//        String[] classifications = productLine.getClassifications().split("-");
//        String[][] keys = productLine.getProducts().keySet().stream()
//                .map(key -> key.split("-"))
//                .toArray(String[][]::new);
//        model.addAttribute("productLine",productLine);
//        model.addAttribute("products",productLine.getProducts());
//        model.addAttribute("classifications",classifications);
//        model.addAttribute("keys",keys);
        return "product";
    }
}

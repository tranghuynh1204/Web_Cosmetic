package hcmute.web_cosmetic.client.product;

import hcmute.web_cosmetic.entity.product.ProductLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    ProductLineService service;

    @GetMapping("/product/{nameProductLine}")
    public String viewProduct(@PathVariable String nameProductLine, Model model){

        ProductLine productLine = service.get(nameProductLine);
        String[] classifications = productLine.getClassifications().split("-");
        String[][] keys = productLine.getProducts().keySet().stream()
                .map(key -> key.split("-"))
                .toArray(String[][]::new);
        model.addAttribute("productLine",productLine);
        model.addAttribute("products",productLine.getProducts());
        model.addAttribute("classifications",classifications);
        model.addAttribute("keys",keys);
        return "client/product";
    }
}

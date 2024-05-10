package banana_cosmetic.client.product;

import banana_cosmetic.client.cart.CartService;
import banana_cosmetic.common.entity.cart.Cart;
import banana_cosmetic.common.entity.cart.LineItem;
import banana_cosmetic.common.entity.product.Product;
import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductLineService service;

    @PostMapping("a")
    public String view(@RequestBody LineItem lineItem ){
        return "product";
    }
    @GetMapping("")
    public String viewProduct(Model model){

        ProductLine productLine = service.get(1L);
        model.addAttribute("productLine",productLine);
        return "product";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<ProductLine> productLines = service.searchProductLines(keyword);
        model.addAttribute("productLines", productLines);
        return "search-result"; // Tạo trang giao diện để hiển thị kết quả tìm kiếm
    }
}

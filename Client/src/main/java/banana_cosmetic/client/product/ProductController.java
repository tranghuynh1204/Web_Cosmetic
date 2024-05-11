package banana_cosmetic.client.product;

import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductLineService service;

    @GetMapping("/{productId}")
    public String viewProduct(@PathVariable Long productId, Model model) {
        ProductLine productLine = service.get(productId);
        model.addAttribute("productLine", productLine);
        return "product";
    }


    @PostMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<ProductLineDto> productLines = service.searchProductLines(keyword);

        if (productLines.isEmpty()) {
            model.addAttribute("noResults", true);
        } else {
            model.addAttribute("productLines", productLines);
        }
        model.addAttribute("keyword", keyword);
        return "searchProduct/search-result"; // Returns the name of the HTML template to display search results
    }


    @PostMapping("/search-classify")
    public String searchProductByBrand(@RequestParam("keyword") String keyword,@RequestParam("category") String category, @RequestParam("brand") String brand, Model model) {
        if (category.isEmpty()) {
            List<ProductLineDto> productLines = service.searchProductLinesByBrand(keyword, brand);

            if (productLines.isEmpty()) {
                model.addAttribute("noResults", true);
            } else {
                model.addAttribute("productLines", productLines);
            }
            model.addAttribute("keyword", keyword);
        } else if (brand.isEmpty()) {
            List<ProductLineDto> productLines = service.searchProductLinesByCategory(keyword, category);

            if (productLines.isEmpty()) {
                model.addAttribute("noResults", true);
            } else {
                model.addAttribute("productLines", productLines);
            }
            model.addAttribute("keyword", keyword);
        }
        else if (!brand.isEmpty() && !category.isEmpty()) {
            List<ProductLineDto> productLines = service.searchProductLinesByCategoryAndBrand(keyword, category,brand);

            if (productLines.isEmpty()) {
                model.addAttribute("noResults", true);
            } else {
                model.addAttribute("productLines", productLines);
            }
            model.addAttribute("keyword", keyword);
        }

        return "searchProduct/search-result";
    }

    @GetMapping("/search-index")
    public String showSearchPage() {
        return "searchProduct/search"; // Returns the name of the HTML template to display the search page
    }
}

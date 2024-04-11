package banana_cosmetic.admin.product;

import banana_cosmetic.common.entity.product.ProductLine;
import banana_cosmetic.common.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductLineService productLineService;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(1, "asc", null,"asc",null,"asc",null, model);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable int pageNum,
                             String nameDir, String name,
                             String categoryDir, String categoryName,
                             String brandDir, String brandName,
                             Model model) {
        PaginationUtil<ProductLine> pageInfo = new PaginationUtil<>(model);
        nameDir = (nameDir == null || nameDir.trim().isEmpty()) ? "asc" : nameDir;
        categoryDir = (categoryDir == null || categoryDir.trim().isEmpty()) ? "asc" : categoryDir;
        brandDir = (brandDir == null || brandDir.trim().isEmpty()) ? "asc" : brandDir;
        List<ProductLineDto> productLines = productLineService.listByPage(pageInfo, pageNum,  nameDir,  name, categoryDir,  categoryName, brandDir,  brandName);
        model.addAttribute("productLines", productLines);
        model.addAttribute("nameDir", nameDir);
        model.addAttribute("name", name);
        model.addAttribute("categoryDir", categoryDir);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("brandDir", brandDir);
        model.addAttribute("brandName", brandName);
        return "product/productlines";
    }
}

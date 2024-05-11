package banana_cosmetic.admin.product;

import banana_cosmetic.admin.brand.BrandDto;
import banana_cosmetic.admin.brand.BrandService;
import banana_cosmetic.admin.category.CategoryDto;
import banana_cosmetic.admin.category.CategoryService;
import banana_cosmetic.common.entity.product.Product;
import banana_cosmetic.common.entity.product.ProductLine;
import banana_cosmetic.common.util.CloudinaryUtil;
import banana_cosmetic.common.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product-lines")
public class ProductLineController {

    @Autowired
    private ProductLineService productLineService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(1, "asc", "name", null, null, null, false, model);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable int pageNum,
                             String sortDir,
                             String sortField,
                             String name, Long brandId, Long categoryId,
                             @RequestParam(name = "isSale", required = false, defaultValue = "false") boolean isSale,
                             Model model) {
        sortDir = (sortDir == null || sortDir.trim().isEmpty()) ? "asc" : sortDir;
        sortField = (sortField == null || sortField.trim().isEmpty()) ? "name" : sortField;
        PaginationUtil pageInfo = new PaginationUtil(model);
        List<ProductLineDto> productLines = productLineService.listByPage(pageInfo, pageNum, sortDir, sortField, name, brandId, categoryId, isSale);
        List<CategoryDto> categories = categoryService.getAll();
        List<BrandDto> brands = brandService.getAll();
        model.addAttribute("productLines", productLines);
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        return "product/product-lines";
    }

    @GetMapping("/add")
    public String addProductLine(Model model) {
        model.addAttribute("productLine", new ProductLine());
        model.addAttribute("title", "Thêm dòng sản phẩm");
        List<CategoryDto> categories = categoryService.getAll();
        List<BrandDto> brands = brandService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        return "product/product-line_form";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveProductLine(ProductLine productLine, @RequestParam("image") String image) {
        try {
            if(productLine.getId()!=null){
                ProductLine oldProductLine = productLineService.get(productLine.getId());
                productLine.setProducts(oldProductLine.getProducts());
                productLine.setClassifications(oldProductLine.getClassifications());
            }
            productLineService.save(productLine);
            if (image != null && !image.isEmpty()) {
                CloudinaryUtil.uploadImage(image, "productline_" + productLine.getId());
            }
            return new ResponseEntity<>("Lưu thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

    @GetMapping("/edit/{id}")
    public String editProductLine(Model model, @PathVariable Long id, RedirectAttributes red) {
        try {
            ProductLine productLine = productLineService.get(id);
            model.addAttribute("productLine", productLine);
            List<CategoryDto> categories = categoryService.getAll();
            List<BrandDto> brands = brandService.getAll();
            model.addAttribute("categories", categories);
            model.addAttribute("brands", brands);
        } catch (Exception e) {
            red.addFlashAttribute("message", e.getMessage());
            return "redirect:/product-lines";
        }
        model.addAttribute("title", "Sửa dòng sản phẩm");
        return "product/product-line_form";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteProductLine(@PathVariable Long id) {
        try {
            productLineService.delete(id);
            CloudinaryUtil.deleteImage("productline_" + id);
            return new ResponseEntity<>("Xoá thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/products")
    public String editProducts(@PathVariable Long id, Model model, RedirectAttributes red) {
        try {
            ProductLine productLine = productLineService.get(id);
            model.addAttribute("products", productLine.getProducts());
            model.addAttribute("classifications", productLine.getClassifications());
            model.addAttribute("title", productLine.getName());
            model.addAttribute("id", id);
        } catch (Exception e) {
            red.addFlashAttribute("message", e.getMessage());
            return "redirect:/product-lines";
        }
        return "product/products";
    }

    @PostMapping("/{id}/products/save")
    @ResponseBody
    public ResponseEntity<String> saveProducts(@RequestBody ProductLine productLineUpdate) {
        try {
            ProductLine productLine = productLineService.get(productLineUpdate.getId());
            productLine.updateProducts(productLineUpdate.getProducts());
            productLine.setClassifications(productLineUpdate.getClassifications());
            productLineService.save(productLine);
            return new ResponseEntity<>("Lưu thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

    @PostMapping("/{id}/product/save")
    @ResponseBody
    public ResponseEntity<String> saveProduct(@RequestBody Product productUpdate) {
        try {
            Product product = productService.get(productUpdate.getId());
            product.updateImages(productUpdate.getImages());
            productService.save(product);
            return new ResponseEntity<>("Lưu thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }
}

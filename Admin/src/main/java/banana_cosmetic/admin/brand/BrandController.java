package banana_cosmetic.admin.brand;

import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.brand.BrandDto;
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

@Controller
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService service;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(1, "asc", null, model);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable int pageNum, String sortDir, String keyWord, Model model) {

        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";
        }
        PaginationUtil<Brand> pageInfo = new PaginationUtil<>(model);
        List<BrandDto> brands = service.listByPage(pageInfo, pageNum, sortDir, keyWord);
        model.addAttribute("brands", brands);
        return "brand/brands";
    }

    @GetMapping("/add")
    public String addBrand(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("title", "Thêm thương hiệu");
        return "brand/brand_form";
    }

    @GetMapping("/edit/{id}")
    public String editBrand(Model model, @PathVariable Long id, RedirectAttributes red) {
        try {
            Brand brand = service.get(id);
            model.addAttribute("brand", brand);
        } catch (Exception e) {
            red.addFlashAttribute("message", e.getMessage());
            return "redirect:/brands";
        }
        model.addAttribute("title", "Sửa thương hiệu");
        return "brand/brand_form";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveBrand(Brand brand, @RequestParam("image") String image) {
        try {
            service.save(brand);
            if (image != null && !image.isEmpty()) {
                CloudinaryUtil.uploadImage(image, brand.getId());
            }
            return new ResponseEntity<>("Lưu thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        try {
            service.delete(id);
            CloudinaryUtil.deleteImage("brand_" + id);
            return new ResponseEntity<>("Xoá thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

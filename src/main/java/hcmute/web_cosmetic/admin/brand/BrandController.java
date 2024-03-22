package hcmute.web_cosmetic.admin.brand;

import hcmute.web_cosmetic.Util.Base64ImageUtil;
import hcmute.web_cosmetic.Util.PaginationUtil;
import hcmute.web_cosmetic.entity.brand.Brand;
import hcmute.web_cosmetic.entity.brand.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import hcmute.web_cosmetic.Util.CloudinaryUtil;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {

    @Autowired
    private BrandService service;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(1, "asc", null, model);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable int pageNum, String sortDir, String keyWord, Model model) {

        PaginationUtil<Brand> pageInfo = new PaginationUtil<>(model);
        List<BrandDto> brands = service.listByPage(pageInfo, pageNum, sortDir, keyWord);
        return "admin/brand/brands";
    }

    @GetMapping("/add")
    public String addBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/brand/brand_form";
    }

    @GetMapping("/edit/{id}")
    public String editBrand(Model model, @PathVariable Long id, RedirectAttributes red) {
        try {
            Brand brand = service.get(id);
            model.addAttribute("brand", brand);
        } catch (Exception e) {
            red.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/brands";
        }
        return "admin/brand/brand_form";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveBrand(Brand brand, @RequestParam("image") String image) {
        try {
            service.save(brand);
            if (image != null && !image.isEmpty()) {
                CloudinaryUtil.uploadImage(image, brand.getId());
            }
            return new ResponseEntity<>("lưu thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Long id, RedirectAttributes red) {
        try {
            service.delete(id);
            red.addFlashAttribute("message", "Xoá thành công");
        } catch (Exception e) {
            red.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/brands";
    }
}

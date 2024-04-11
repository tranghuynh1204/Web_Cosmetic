package banana_cosmetic.admin.category;

import banana_cosmetic.common.entity.category.Category;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(1, "asc", null, model);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable int pageNum, String nameDir, String name, Model model) {

        nameDir = (nameDir == null || nameDir.trim().isEmpty()) ? "asc" : nameDir;
        PaginationUtil<Category> pageInfo = new PaginationUtil<>(model);
        List<CategoryDto> categories = service.listByPage(pageInfo, pageNum, nameDir, name);
        model.addAttribute("categories", categories);
        model.addAttribute("nameDir", nameDir);
        model.addAttribute("name", name);
        return "category/categories";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        List<CategoryDto> categories = service.getAll();
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Thêm danh mục");
        return "category/category_form";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model, RedirectAttributes red) {

        try {
            Category category = service.get(id);
            Category cateClone = category.clone();
            List<CategoryDto> categories = service.getAll();
            model.addAttribute("category", cateClone);
            model.addAttribute("categories", categories);
            model.addAttribute("title", "Sửa danh mục");
        } catch (Exception ex) {
            red.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
        return "category/category_form";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveCategory(Category category) {
        try {
            service.save(category);
            return new ResponseEntity<>("Lưu thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {

        try {
            service.delete(id);
            return new ResponseEntity<>("Xoá thành công", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

package hcmute.web_cosmetic.admin.category;

import hcmute.web_cosmetic.entity.category.Category;
import hcmute.web_cosmetic.entity.category.CategoryDto;
import hcmute.web_cosmetic.Util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("")
    public List<CategoryDto> listFirstPage(String sortDir, Model model) {
        return listByPage(1, sortDir, null, model);
    }

    @GetMapping("/page/{pageNum}")
    public List<CategoryDto> listByPage(@PathVariable int pageNum, String sortDir, String keyWord, Model model) {

        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "asc";
        }
        PaginationUtil pageInfo = new PaginationUtil(model);
        List<CategoryDto> categories = service.listByPage(pageInfo, pageNum, sortDir, keyWord);
        return categories;
    }

    @GetMapping("/new")
    public String newCategory(Model model) {

        List<CategoryDto> categories = service.getAll();

        model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);

        return "admin/category/category_form";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model, RedirectAttributes red) {

        try {
            List<CategoryDto> categories = service.getAll();
            Category category = service.get(id);

            model.addAttribute("category", category);
            model.addAttribute("categories", categories);

            return "admin/category/category_form";
        } catch (Exception ex) {
            red.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/categories";
        }
    }

    @PostMapping("/save")
    public String saveCategory(Category category, RedirectAttributes red) {
        service.save(category);
        red.addFlashAttribute("message", "Thêm danh mục thành công");
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes red) {

        try {
            service.delete(id);
            red.addFlashAttribute("message", "Danh mục có id là " + id + "Đã bị xoá thành công");
        } catch (Exception ex) {
            red.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/admin/categories";
    }


}

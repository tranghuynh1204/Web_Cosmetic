package banana_cosmetic.common.util;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class PaginationUtil<T> {
    private final Model model;

    public PaginationUtil(Model model) {
        this.model = model;
    }

    public void addAttribute(Page<T> page) {
        int pageSize = page.getSize();

        long startCount = (long) page.getNumber() * page.getSize() + 1;
        long endCount = startCount + pageSize - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", page.getNumber() + 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
    }
}

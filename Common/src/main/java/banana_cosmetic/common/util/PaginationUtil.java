package banana_cosmetic.common.util;
import org.javatuples.Pair;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class PaginationUtil {
    private final Model model;

    public PaginationUtil(Model model) {
        this.model = model;
    }

    public <T> void addAttribute(Page<T> page, String sortDir, String sortField,Pair ... keywords) {
        int pageSize = page.getSize();

        long startCount = (long) page.getNumber() * page.getSize() + 1;
        long endCount = startCount + pageSize - 1;

        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        for (var keyword : keywords) {
            model.addAttribute(keyword.getValue0().toString(), keyword.getValue1());
        }
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sortField", sortField);
        model.addAttribute("currentPage", page.getNumber() + 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());

    }

}

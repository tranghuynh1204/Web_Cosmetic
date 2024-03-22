package hcmute.web_cosmetic.admin.category;

import hcmute.web_cosmetic.entity.category.Category;
import hcmute.web_cosmetic.entity.category.CategoryDto;
import hcmute.web_cosmetic.Util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final int ROOT_CATEGORIES_PER_PAGE = 5;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper mapper;

    public void save(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentIds() == null ? "-" : parent.getAllParentIds();
            allParentIds += parent.getId() + "-";
            category.setAllParentIds(allParentIds);
        }
        repository.save(category);
    }

    public void delete(Long id) throws Exception {
        Category category = get(id);
        repository.delete(category);
    }

    public List<CategoryDto> getAll() {
        Sort sort = Sort.by("name");
        List<Category> categories = repository.findByParentIsNull(sort);
        categories = hierarchicalCategories(categories, "asc");
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    public List<CategoryDto> listByPage(PaginationUtil pageInfo, int pageNum, String sortDir, String keyWord) {
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

        Page<Category> pageCategories;
        List<Category> categories;

        if (keyWord == null || keyWord.isEmpty()) {
            pageCategories = repository.findByParentIsNull(pageable);
            categories = pageCategories.getContent();
            categories = hierarchicalCategories(categories, sortDir);
        } else {
            pageCategories = repository.findByNameContainingIgnoreCase(pageable, keyWord);
            categories = pageCategories.getContent();
        }

        pageInfo.addAttribute(pageCategories);

        return categories.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public List<Category> hierarchicalCategories(List<Category> rootCategories, String sortDir) {

        List<Category> categories = new ArrayList<>();

        for (Category category : rootCategories) {
            categories.add(category);
            if (!category.getChildren().isEmpty()) {
                List<Category> subCategories = listSubHierarchicalCategories(category.getChildren(), sortDir, 1);
                categories.addAll(subCategories);
            }
        }

        return categories;
    }

    public List<Category> listSubHierarchicalCategories(List<Category> subCategories, String sortDir, int subLevel) {

        sortCategories(subCategories, sortDir);

        List<Category> categories = new ArrayList<>();

        for (Category category : subCategories) {
            String name = "--".repeat(subLevel) + category.getName();

            categories.add(category);

            category.setName(name);
            if (!category.getChildren().isEmpty()) {
                List<Category> listSubCategories = listSubHierarchicalCategories(category.getChildren(), sortDir, subLevel + 1);
                categories.addAll(listSubCategories);
            }
        }

        return categories;
    }

    public void sortCategories(List<Category> categories, String sortDir) {
        categories.sort((cat1, cat2) -> {
            if (sortDir.equals("asc")) {
                return cat1.getName().compareTo(cat2.getName());
            } else {
                return cat2.getName().compareTo(cat1.getName());
            }
        });
    }

    public Category get(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy danh mục với ID: " + id));
    }
}

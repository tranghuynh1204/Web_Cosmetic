package banana_cosmetic.admin.category;

import banana_cosmetic.common.entity.category.Category;
import banana_cosmetic.common.entity.category.CategoryDto;
import banana_cosmetic.common.util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final int ROOT_CATEGORIES_PER_PAGE = 5;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper mapper;

    public void save(Category category) {
        Long id = category.getId();
        if (category.getId() == null) {
            setAllParentIdsAndSave(category);
        } else {
            Optional<Category> oldCategory = repository.findById(id);
            List<Category> children = oldCategory.get().getChildren();
            if (children != null && !children.isEmpty()) {
                category.setChildren(children);
            }
            if (category.getParent()==null || category.getParent().getId() != oldCategory.get().getId()) {
                setAllParentIdsAndSave(category);
            } else {
                repository.save(category);
            }
        }

    }

    private void setAllParentIdsAndSave(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentIds() == null ? "-" : parent.getAllParentIds();
            allParentIds += parent.getId() + "-";
            category.setAllParentIds(allParentIds);
        }
        repository.save(category);
        List<Category> children = category.getChildren();
        if (children != null) {
            for (Category child : children) {
                setAllParentIdsAndSave(child);
            }
        }
    }

    public void delete(Long id) throws Exception {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Vẫn còn sản phẩm mang danh mục này.");
        } catch (Exception e) {
            throw new Exception("Lỗi xóa danh mục: " + e.getMessage());
        }
    }

    public List<CategoryDto> getAll() {
        Sort sort = Sort.by("name");
        List<Category> categories = repository.findByParentIsNull(sort);
        categories = hierarchicalCategories(categories, "asc");
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public List<CategoryDto> listByPage(PaginationUtil<Category> pageInfo, int pageNum, String sortDir,
            String keyWord) {
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

        pageInfo.addAttribute(pageCategories, sortDir, "name", keyWord);

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
                List<Category> listSubCategories = listSubHierarchicalCategories(category.getChildren(), sortDir,
                        subLevel + 1);
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

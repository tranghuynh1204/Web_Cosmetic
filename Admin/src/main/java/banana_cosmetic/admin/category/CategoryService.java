package banana_cosmetic.admin.category;

import banana_cosmetic.admin.product.ProductLineRepository;
import banana_cosmetic.common.entity.category.Category;
import banana_cosmetic.common.util.PaginationUtil;
import org.javatuples.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private static final int ROOT_CATEGORIES_PER_PAGE = 9;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductLineRepository productLineRepository;

    public void save(Category category) throws Exception {
        Long id = category.getId();
        if (category.getId() == null) {
            setAllParentIdsAndSave(category);
        } else {
            Optional<Category> oldCategory = repository.findById(id);
            List<Category> children = oldCategory.get().getChildren();
            if (children != null && !children.isEmpty()) {
                category.setChildren(children);
            }
            if (category.getParent() == null || category.getParent().getId() != oldCategory.get().getId()) {
                setAllParentIdsAndSave(category);
            } else {
                try {
                    repository.save(category);
                } catch (DataIntegrityViolationException e) {
                    throw new Exception("Danh mục " + category.getName() + " đã tồn tại.");
                }
            }
        }

    }

    private void setAllParentIdsAndSave(Category category) throws Exception {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentIds() == null ? "-" : parent.getAllParentIds();
            allParentIds += parent.getId() + "-";
            category.setAllParentIds(allParentIds);
        }
        try {
            repository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Danh mục " + category.getName() + " đã tồn tại.");
        }
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
            throw new Exception("Vẫn còn sản phẩm thuộc danh mục này.");
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("Không tìm thấy danh mục có id: " + id);
        }
    }

    public List<CategoryDto> getAll() {
        Sort sort = Sort.by("name");
        List<Category> categories = repository.findByParentIsNull(sort);
        categories = hierarchicalCategories(categories, "asc");
        return categories.stream().map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public List<CategoryDto> listByPage(PaginationUtil pageInfo, int pageNum, String sortDir,
                                        String name) {
        Sort sort = Sort.by(sortDir.equals("asc") ?
                Sort.Order.asc("name") :
                Sort.Order.desc("name"));

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

        Page<Category> pageCategories;
        List<Category> categories;

        if (name == null || name.isEmpty()) {
            pageCategories = repository.findByParentIsNull(pageable);
            categories = pageCategories.getContent();
            categories = hierarchicalCategories(categories, sortDir);
        } else {
            pageCategories = repository.findByNameContainingIgnoreCase(pageable, name);
            categories = pageCategories.getContent();
            for (Category category : categories) {
                category.setHasChildren(!category.getChildren().isEmpty());
            }
        }

        pageInfo.addAttribute(pageCategories, sortDir, null, Pair.with("name",name ));

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
                category.setHasChildren(true);
            } else {
                category.setHasChildren(false);
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
                category.setHasChildren(true);
            } else {
                category.setHasChildren(false);
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

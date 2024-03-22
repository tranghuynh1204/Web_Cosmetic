package hcmute.web_cosmetic.admin.category;

import hcmute.web_cosmetic.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNull(Sort sort);

    Page<Category> findByParentIsNull(Pageable pageable);

    Page<Category> findByNameContainingIgnoreCase(Pageable pageable, String keyWord);
}

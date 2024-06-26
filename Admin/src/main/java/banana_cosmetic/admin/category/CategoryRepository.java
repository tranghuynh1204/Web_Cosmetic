package banana_cosmetic.admin.category;

import banana_cosmetic.common.entity.category.Category;
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

    @Query("select distinct c from Category c left join fetch c.children where c.parent is null")
    Page<Category> findByParentIsNull(Pageable pageable);

    Page<Category> findByNameContainingIgnoreCase(Pageable pageable, String name);

}

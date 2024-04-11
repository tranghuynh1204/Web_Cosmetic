package banana_cosmetic.admin.brand;

import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Page<Brand> findByNameContainingIgnoreCase(Pageable pageable, String name);

}

package hcmute.web_cosmetic.admin.brand;

import hcmute.web_cosmetic.entity.brand.Brand;
import hcmute.web_cosmetic.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Page<Brand> findByNameContainingIgnoreCase(Pageable pageable, String keyWord);

}

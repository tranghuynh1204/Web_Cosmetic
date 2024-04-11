package banana_cosmetic.admin.product;

import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
    @Query("SELECT p FROM ProductLine p WHERE " +
            "    (LOWER(p.name) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%')))" +
            "    AND LOWER(p.category.name) LIKE LOWER(CONCAT('%', COALESCE(:categoryName, ''), '%'))" +
            "    AND LOWER(p.brand.name) LIKE LOWER(CONCAT('%', COALESCE(:brandName, ''), '%'))")
    Page<ProductLine> getAll(Pageable pageable, String name, String categoryName, String brandName);

}

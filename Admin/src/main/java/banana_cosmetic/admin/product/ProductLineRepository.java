package banana_cosmetic.admin.product;

import banana_cosmetic.common.entity.product.Product;
import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
    @Query("SELECT p FROM ProductLine p WHERE " +
            "(:brandId IS NULL OR p.brand.id = :brandId)" +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId OR p.category.allParentIds LIKE CONCAT('%', :categoryId, '%')) " +
            "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))) "
    )
    Page<ProductLine> getAll(Pageable pageable, String name, Long categoryId, Long brandId);

}

package banana_cosmetic.client.product;

import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine,Long> {
    List<ProductLine> findByNameContainingIgnoreCase(String keyword);

    List<ProductLine> findByBrandNameContainingIgnoreCase(String keyword);

    List<ProductLine> findByBrandNameContainingIgnoreCaseAndNameContainingIgnoreCase(String brand, String keyword);

    List<ProductLine> findByCategoryNameContainingIgnoreCaseAndNameContainingIgnoreCase(String category, String keyword);

    List<ProductLine> findByCategoryNameContainingIgnoreCaseAndBrandNameContainingIgnoreCaseAndNameContainingIgnoreCase(String category, String brand, String keyword);
}

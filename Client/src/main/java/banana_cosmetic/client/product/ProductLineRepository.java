package banana_cosmetic.client.product;

import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine,Long> {
    List<ProductLine> findByNameContainingIgnoreCase(String keyword);

}

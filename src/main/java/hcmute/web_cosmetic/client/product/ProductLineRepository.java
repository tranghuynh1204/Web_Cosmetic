package hcmute.web_cosmetic.client.product;

import hcmute.web_cosmetic.entity.product.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine,Long> {
    ProductLine findByName(String nameProductLine);
}

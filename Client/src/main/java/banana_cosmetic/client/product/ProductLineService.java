package banana_cosmetic.client.product;

import banana_cosmetic.common.entity.product.ProductLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductLineService {

    @Autowired
    private ProductLineRepository repository;

    public ProductLine get(Long id) {
        return repository.findById(id).orElse(null);
    }
}

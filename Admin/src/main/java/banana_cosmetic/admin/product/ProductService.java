package banana_cosmetic.admin.product;

import banana_cosmetic.common.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product get(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy sản phẩm với ID: " + id + "."));
    }

    public void save(Product product) {
        repository.save(product);
    }
}

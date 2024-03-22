package hcmute.web_cosmetic.client.product;

import hcmute.web_cosmetic.entity.product.ProductLine;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductLineService {

    @Autowired
    private ProductLineRepository repository;
    public ProductLine get(String nameProductLine) {
        return repository.findByName(nameProductLine);
    }
}

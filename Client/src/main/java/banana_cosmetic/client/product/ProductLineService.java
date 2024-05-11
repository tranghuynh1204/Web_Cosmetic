package banana_cosmetic.client.product;

import banana_cosmetic.common.entity.product.ProductLine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLineService {

    @Autowired
    private ProductLineRepository repository;

    @Autowired
    private ModelMapper mapper;

    public ProductLine get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ProductLineDto> searchProductLines(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword).stream()
                .map(brand -> mapper.map(brand, ProductLineDto.class))
                .collect(Collectors.toList());
    }
}

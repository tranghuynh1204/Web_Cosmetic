package banana_cosmetic.admin.product;

import banana_cosmetic.admin.category.CategoryDto;
import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.product.ProductLine;
import banana_cosmetic.common.util.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLineService {

    private static final int PRODUCTLINE_PER_PAGE = 10;
    @Autowired
    private ProductLineRepository productLineRepository;
    @Autowired
    private ModelMapper mapper;

    public List<ProductLineDto> listByPage(PaginationUtil<ProductLine> pageInfo, int pageNum,
                                           String nameDir, String name,
                                           String categoryDir, String categoryName,
                                           String brandDir, String brandName) {

        Sort sort = Sort.by(
                nameDir.equals("asc") ?
                        Sort.Order.asc("name") : Sort.Order.desc("name"),
                categoryDir.equals("asc") ?
                        Sort.Order.asc("category.name") : Sort.Order.desc("category.name"),
                brandDir.equals("asc") ?
                        Sort.Order.asc("brand.name") : Sort.Order.desc("brand.name")
        );
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTLINE_PER_PAGE, sort);

        Page<ProductLine> pageProductLines = productLineRepository.getAll(pageable, name, categoryName, brandName);
        List<ProductLine> productLines = pageProductLines.getContent();

        return productLines.stream()
                .map(category -> mapper.map(category, ProductLineDto.class))
                .collect(Collectors.toList());
    }
}

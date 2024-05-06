package banana_cosmetic.admin.product;

import banana_cosmetic.common.entity.product.Product;
import banana_cosmetic.common.entity.product.ProductLine;
import banana_cosmetic.common.util.PaginationUtil;
import org.javatuples.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductLineService {

    private static final int PRODUCTLINE_PER_PAGE = 10;
    @Autowired
    private ProductLineRepository repository;
    @Autowired
    private ModelMapper mapper;

    public List<ProductLineDto> listByPage(PaginationUtil pageInfo, int pageNum, String sortDir, String sortField, String name, Long brandId, Long categoryId, boolean isSale) {
        Sort sort = Sort.by(sortDir.equals("asc") ?
                Sort.Order.asc(sortField) :
                Sort.Order.desc(sortField));
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTLINE_PER_PAGE, sort);

        Page<ProductLine> pageProductLines;
        if (isSale) {
            pageProductLines = repository.getAllSale(pageable, name, categoryId, brandId);
        }
        else{
            pageProductLines = repository.getAll(pageable, name, categoryId, brandId);
        }
        List<ProductLine> productLines = pageProductLines.getContent();
        pageInfo.addAttribute(pageProductLines, sortDir, sortField, Pair.with("name", name), Pair.with("brandId", brandId), Pair.with("categoryId", categoryId),Pair.with("isSale",isSale));
        return productLines.stream()
                .map(productLine -> mapper.map(productLine, ProductLineDto.class))
                .collect(Collectors.toList());
    }

    public void save(ProductLine productLine) throws Exception {
        try {
            repository.save(productLine);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Dòng sản phẩm " + productLine.getName() + " đã tồn tại.");
        }
    }

    public ProductLine get(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy dòng sản phẩm với ID: " + id + "."));
    }


    public void delete(Long id) throws Exception {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("Không tìm thấy dòng sản phẩm có id: " + id);
        }
    }

    public void update(Long id, Map<String, Product> products, String classifications) {

    }
}

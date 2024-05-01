package banana_cosmetic.admin.brand;

import banana_cosmetic.common.entity.brand.Brand;
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
import java.util.stream.Collectors;

@Service
public class BrandService {

    private static final int BRAND_PER_PAGE = 15;
    @Autowired
    private BrandRepository repository;
    @Autowired
    private ModelMapper mapper;

    public List<BrandDto> listByPage(PaginationUtil pageInfo, int pageNum, String sortDir, String name) {

        Sort sort = Sort.by(sortDir.equals("asc") ?
                Sort.Order.asc("name") :
                Sort.Order.desc("name"));

        Pageable pageable = PageRequest.of(pageNum - 1, BRAND_PER_PAGE, sort);

        Page<Brand> pageBrands;
        List<Brand> brands;

        if (name == null || name.isEmpty()) {
            pageBrands = repository.findAll(pageable);
        } else {
            pageBrands = repository.findByNameContainingIgnoreCase(pageable, name);
        }
        brands = pageBrands.getContent();

        pageInfo.addAttribute(pageBrands, sortDir, null, Pair.with("name", name));

        return brands.stream()
                .map(brand -> mapper.map(brand, BrandDto.class))
                .collect(Collectors.toList());
    }

    public void save(Brand brand) throws Exception {
        try {
            repository.save(brand);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Thương hiệu " + brand.getName() + " đã tồn tại.");
        }
    }

    public Brand get(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy thương hiệu với ID: " + id + "."));
    }

    public void delete(Long id) throws Exception {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Vẫn còn sản phẩm mang thương hiệu này.");
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("Không tìm thấy thương hiệu có id: " + id);
        }
    }

    public List<BrandDto> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name"); // Sắp xếp tăng dần theo trường "name"
        return repository.findAll(sort).stream()
                .map(brand -> mapper.map(brand, BrandDto.class))
                .collect(Collectors.toList());
    }
}

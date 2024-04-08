package banana_cosmetic.admin.brand;

import banana_cosmetic.common.entity.brand.Brand;
import banana_cosmetic.common.entity.brand.BrandDto;
import banana_cosmetic.common.util.PaginationUtil;
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

    public List<BrandDto> listByPage(PaginationUtil<Brand> pageInfo, int pageNum, String sortDir, String keyWord) {

        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, BRAND_PER_PAGE, sort);

        Page<Brand> pageBrands;
        List<Brand> brands;

        if (keyWord == null || keyWord.isEmpty()) {
            pageBrands = repository.findAll(pageable);
        } else {
            pageBrands = repository.findByNameContainingIgnoreCase(pageable, keyWord);
        }
        brands = pageBrands.getContent();

        pageInfo.addAttribute(pageBrands, sortDir, "name", keyWord);

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
            throw new Exception("Vẫn còn sản phẩm mang thương hiệu có ID: " + id);
        } catch (Exception e) {
            throw new Exception("Lỗi xóa thương hiệu: " + e.getMessage());
        }
    }
}

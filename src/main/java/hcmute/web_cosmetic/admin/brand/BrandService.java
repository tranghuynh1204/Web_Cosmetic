package hcmute.web_cosmetic.admin.brand;

import hcmute.web_cosmetic.Util.PaginationUtil;
import hcmute.web_cosmetic.entity.brand.Brand;
import hcmute.web_cosmetic.entity.brand.BrandDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private static final int BRAND_PER_PAGE = 5;
    @Autowired
    private BrandRepository repository;
    @Autowired
    private ModelMapper mapper;

    public List<BrandDto> listByPage(PaginationUtil pageInfo, int pageNum, String sortDir, String keyWord) {

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

        pageInfo.addAttribute(pageBrands);

        return brands.stream()
                .map(brand -> mapper.map(brand, BrandDto.class))
                .collect(Collectors.toList());
    }

    public void save(Brand brand) throws Exception {
        try {
            repository.save(brand);
        }catch (DataIntegrityViolationException e) {
            throw new Exception("Thương hiệu "+brand.getName()+" đã tồn tại.");
        }
    }

    public Brand get(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy thương hiệu với ID: " + id+"."));

    }

    public void delete(Long id) throws Exception {
        Brand brand = get(id);
        try {
            repository.delete(brand);
        }catch (DataIntegrityViolationException e){
            throw new Exception("Vẫn còn sản phẩm mang thương hiệu "+brand.getName()+".");
        }
    }
}

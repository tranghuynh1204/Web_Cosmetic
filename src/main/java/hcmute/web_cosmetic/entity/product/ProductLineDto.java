package hcmute.web_cosmetic.entity.product;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ProductLineDto {

    private String classifications;
    private Map<String, Product> products;

}

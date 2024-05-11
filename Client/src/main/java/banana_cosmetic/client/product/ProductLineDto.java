package banana_cosmetic.client.product;

import lombok.Data;

@Data
public class ProductLineDto {
    private Long id;
    private String name;
    private String image;
    private String displayPrice;
}

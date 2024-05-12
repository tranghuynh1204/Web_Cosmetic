package banana_cosmetic.client.cart;

import lombok.Data;

@Data
public class LineItemDto {
    private Long id;
    private int quantity;
    private String productLineName;
    private String classification;
    private String imageProductLine;
    private Long productId;
    private Long price;
    public long getTotal() {
        return quantity * price;
    }
}

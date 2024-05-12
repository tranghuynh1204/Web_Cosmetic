package banana_cosmetic.admin.order;

import lombok.Data;

import java.util.Date;
@Data
public class OrderDto {
    private Long id;
    private Date orderTime;
    private Date timeReceived;
    private String nameCustomer;
    private String phone;
    private String address;
    private Long total;
    private String status;

}

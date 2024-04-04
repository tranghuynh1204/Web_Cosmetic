package banana_cosmetic.common.entity.order;

import banana_cosmetic.common.entity.IdBasedEntity;
import banana_cosmetic.common.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name =  "\"order\"")
public class Order extends IdBasedEntity {

    private Date orderTime;
    private Date timeReceived;
    private String nameCustomer;
    private String phone;
    private String address;
    private Long total;

    @ManyToOne
    private User customer;
    @JoinColumn(name = "order_id")
    @OneToMany
    private List<OrderDetail> details;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}

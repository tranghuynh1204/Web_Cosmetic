package hcmute.web_cosmetic.entity.order;

import hcmute.web_cosmetic.entity.Customer;
import hcmute.web_cosmetic.entity.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.id.uuid.CustomVersionOneStrategy;

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
    private Customer customer;
    @JoinColumn(name = "order_id")
    @OneToMany
    private List<OrderDetail> details;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}

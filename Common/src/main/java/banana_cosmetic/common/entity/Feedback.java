package banana_cosmetic.common.entity;

import banana_cosmetic.common.entity.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Feedback extends IdBasedEntity{

    @Column(columnDefinition = "TEXT")
    private String comment;
    private int rate;
    private Date feedbackTime;
    @ManyToOne
    private User customer;
    @ManyToOne
    private Product product;
}

package hcmute.web_cosmetic.entity;

import hcmute.web_cosmetic.entity.product.Product;
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

    @Column(length = 4096)
    private String comment;
    private int rate;
    private Date feedbackTime;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Customer customer;
}

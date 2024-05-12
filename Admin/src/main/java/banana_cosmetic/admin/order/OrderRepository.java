package banana_cosmetic.admin.order;

import banana_cosmetic.common.entity.order.Order;
import banana_cosmetic.common.entity.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByStatus(Pageable pageable, OrderStatus status);


    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void updateStatus(Long id, OrderStatus status);
}
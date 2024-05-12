package banana_cosmetic.admin.order;

import banana_cosmetic.common.entity.order.Order;
import banana_cosmetic.common.entity.order.OrderStatus;
import banana_cosmetic.common.util.PaginationUtil;
import org.javatuples.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final int ORDER_PER_PAGE = 10;
    @Autowired
    private OrderRepository repository;

    @Autowired
    private ModelMapper mapper;

    public List<OrderDto> listByPage(PaginationUtil pageInfo, int pageNum, String sortDir, String status) {

        Sort sort = Sort.by(sortDir.equals("asc") ?
                Sort.Order.asc("orderTime") :
                Sort.Order.desc("orderTime"));

        Pageable pageable = PageRequest.of(pageNum - 1, ORDER_PER_PAGE, sort);

        Page<Order> pageOrders;
        List<Order> orders;
        if (status == null) {
            pageOrders = repository.findAll(pageable);
        } else {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            pageOrders = repository.findByStatus(pageable, orderStatus);
        }
        orders = pageOrders.getContent();

        pageInfo.addAttribute(pageOrders, sortDir, null, Pair.with("status", status));

        return orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    public Order get(Long id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy đơn hàng với ID: " + id + "."));
    }

    public void updateStatus(Order order) throws Exception {
        Order oldOrder = get(order.getId());
        oldOrder.setStatus(order.getStatus());
        repository.save(oldOrder);
    }
}
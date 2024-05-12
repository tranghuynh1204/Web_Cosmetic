package banana_cosmetic.client.order;

import banana_cosmetic.common.entity.User;
import banana_cosmetic.common.entity.cart.LineItem;
import banana_cosmetic.common.entity.order.Order;
import banana_cosmetic.common.entity.order.OrderStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")

public class OrderController {

    @Autowired
    private OrderService service;
    @GetMapping("/view/{order_id}")
    public String viewOrder(@PathVariable Long order_id, Model model) {
        Order order = service.getOrder(order_id);
        // Kiểm tra xem đơn hàng có tồn tại không
        if (order != null) {
            // Nếu có, đưa thông tin đơn hàng vào model để hiển thị trên giao diện
            model.addAttribute("order", order);
            return "orderdetails";
        } else {
            // Nếu không tìm thấy đơn hàng, có thể trả về một trang lỗi hoặc thông báo tùy thuộc vào yêu cầu của bạn
            return "error";
        }
    }

    @GetMapping("")
    public String viewAllOrders(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");
        if (customer == null) {
            return "login";
        }
        List<Order> orders = service.findAllOrdersByCustomer(customer);
        model.addAttribute("orders", orders);
        return "vieworders";
    }


    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Long> createNewOrderFromCart(HttpServletRequest request,@RequestBody Order order) {
        try {
            HttpSession session = request.getSession();
            User customer = (User) session.getAttribute("customer");

            order.setCustomer(customer);
            order.setOrderTime(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 15);
            Date timeReceived = calendar.getTime();
            order.setTimeReceived(timeReceived);
            order.setStatus(OrderStatus.PROCESSING);

            service.save(order);
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

package banana_cosmetic.admin.order;

import banana_cosmetic.common.entity.order.Order;
import banana_cosmetic.common.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("")
    public String listFirstPage(Model model) {
        return listByPage(1, "asc", null, model);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable int pageNum, String sortDir, String status, Model model) {

        sortDir = (sortDir == null || sortDir.trim().isEmpty()) ? "asc" : sortDir;
        PaginationUtil pageInfo = new PaginationUtil(model);
        List<OrderDto> orders = service.listByPage(pageInfo, pageNum, sortDir, status);
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @GetMapping("/detail/{id}")
    public String viewOrder(Model model, @PathVariable Long id, RedirectAttributes red) {
        try {
            Order order = service.get(id);
            model.addAttribute("order", order);
        } catch (Exception e) {
            red.addFlashAttribute("message", e.getMessage());
            return "redirect:/order";
        }
        model.addAttribute("title", "Xem đơn hàng");
        return "order/order_form";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveOrder(@RequestBody Order order) {
        try {
            service.updateStatus(order);
            return new ResponseEntity<>("Lưu thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

}
package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.User;
import banana_cosmetic.common.entity.cart.Cart;
import banana_cosmetic.common.entity.cart.LineItem;
import banana_cosmetic.common.entity.product.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @GetMapping("")
    public String viewCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute("customer");
        Cart cart = service.getCart(customer.getId());
        List<LineItemDto> items = new ArrayList<>();
        for (LineItem item : cart.getItems()) {
            LineItemDto dto = new LineItemDto();
            dto.setId(item.getId());
            dto.setQuantity(item.getQuantity());
            Product product = item.getProduct();
            if (product != null) {
                dto.setProductLineName(product.getProductLine().getName());
                dto.setImageProductLine(product.getProductLine().getImage());
                dto.setPrice(product.getCurrentPrice());
                for (var entry : product.getProductLine().getProducts().entrySet()) {
                    if (entry.getValue().getId().equals(product.getId())) {
                        dto.setClassification(entry.getKey());
                        break;
                    }
                }
            }
            items.add(dto);
        }
        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody LineItem lineitem, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User customer = (User) session.getAttribute("customer");
            service.addLineItemToCart(lineitem, customer.getId());
            return new ResponseEntity<>("Thêm thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }

    }

    @DeleteMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeFromCart(@RequestBody List<Long> lineItemIds, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User customer = (User) session.getAttribute("customer");
            service.deleteFromCart(lineItemIds, customer.getId());
            return new ResponseEntity<>("Xoá thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK.
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }

    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateQuantityInCart(@RequestBody LineItem lineitem, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            User customer = (User) session.getAttribute("customer");
            service.updateQuantityInCart(lineitem.getId(), lineitem.getQuantity(), customer.getId());
            return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

}
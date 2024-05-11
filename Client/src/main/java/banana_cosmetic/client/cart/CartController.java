package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.cart.Cart;
import banana_cosmetic.common.entity.cart.LineItem;
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
    public String viewCart(Model model) {
        Cart cart = service.getCart();
        List<LineItemDto> items = new ArrayList<>();
        for (LineItem item : cart.getItems()) {
            LineItemDto dto = new LineItemDto();
            dto.setId(item.getId());
            dto.setQuantity(item.getQuantity());
            dto.setProductLineName(item.getProduct().getProductLine().getName());
            dto.setImageProductLine(item.getProduct().getProductLine().getImage());
            dto.setPrice(item.getProduct().getCurrentPrice());
            for (var entry : item.getProduct().getProductLine().getProducts().entrySet()) {
                if (entry.getValue().getId().equals(item.getProduct().getId())) {
                    dto.setClassification(entry.getKey());
                    break;
                }
            }
            items.add(dto);
        }
        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addToCart(@RequestBody LineItem lineitem){
        try{
            service.addLineItemToCart(lineitem);
            return new ResponseEntity<>("Thêm thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }

    }
    @DeleteMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeFromCart(@RequestBody List<Long> lineItemIds){
        try{
            service.deleteFromCart(lineItemIds);
            return new ResponseEntity<>("Xoá thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK.
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }

    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateQuantityInCart(@RequestBody LineItem lineitem) {
        try{
            service.updateQuantityInCart(lineitem.getId(), lineitem.getQuantity());
            return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

    }
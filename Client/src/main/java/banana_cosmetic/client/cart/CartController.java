package banana_cosmetic.client.cart;

import banana_cosmetic.common.entity.cart.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody LineItem lineitem){
        try{
            service.addLineItemToCart(lineitem);
            return new ResponseEntity<>("Thêm thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }

    }
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody List<Long> lineItemIds){
        try{
            service.deleteFromCart(lineItemIds);
            return new ResponseEntity<>("Xoá thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQuantityInCart(@RequestParam Long id, @RequestParam int quantity) {
        try{
            service.updateQuantityInCart(id, quantity);
            return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK); // Trả về brand mới với mã trạng thái 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về thông báo lỗi với mã trạng thái 400 Bad Request
        }
    }

    }
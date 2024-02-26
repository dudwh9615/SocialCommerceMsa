package Project.moduleshop.controller;

import Project.moduleshop.dto.OrderRequestDto;
import Project.moduleshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Long orderNum = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderNum + "번 주문 생성 완료");
    }

//    @PostMapping("/{orderId}/payments")
//    public ResponseEntity<String> executeOrder(@PathVariable Long orderId) {
//        orderService.executeOrder(orderId);
//        return ResponseEntity.ok(orderId + "번 주문 완료");
//    }

//    @GetMapping
//    public ResponseEntity<OrderResponseDto> getOrderInfo() {
//
//    }
}

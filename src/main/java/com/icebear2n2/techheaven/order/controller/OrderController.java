package com.icebear2n2.techheaven.order.controller;
import com.icebear2n2.techheaven.domain.request.OrderRequest;
import com.icebear2n2.techheaven.domain.request.UpdateOrderStatusRequest;
import com.icebear2n2.techheaven.domain.request.UserIDRequest;
import com.icebear2n2.techheaven.domain.response.OrderResponse;
import com.icebear2n2.techheaven.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        if (orderResponse.isSuccess()) {
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(orderResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse.OrderData>> getAll(
            @RequestBody UserIDRequest userIDRequest,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest request = PageRequest.of(page, size);
        return  new ResponseEntity<>(orderService.getAllByUser(userIDRequest, request), HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<OrderResponse> changeOrderStatus(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        OrderResponse orderResponse = orderService.changeOrderStatus(updateOrderStatusRequest);
        if (orderResponse.isSuccess()) {
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(orderResponse, HttpStatus.BAD_REQUEST);
        }
    }
}

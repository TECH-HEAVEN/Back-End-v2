package com.icebear2n2.techheaven.cart.controller;


import com.icebear2n2.techheaven.cart.service.CartService;
import com.icebear2n2.techheaven.domain.request.CartRequest;
import com.icebear2n2.techheaven.domain.request.UserIDRequest;
import com.icebear2n2.techheaven.domain.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CartRequest cartRequest) {
        CartResponse cartResponse = cartService.createCart(cartRequest);

        if (cartResponse.isSuccess()) {
            return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(cartResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<CartResponse.CartData>> getAll(@RequestBody UserIDRequest userIDRequest,
                                                             @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest request = PageRequest.of(page, size);
        return new ResponseEntity<>(cartService.getAllByUser(userIDRequest, request), HttpStatus.OK);
    }
}

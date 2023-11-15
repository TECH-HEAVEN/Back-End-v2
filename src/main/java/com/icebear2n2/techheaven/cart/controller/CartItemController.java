package com.icebear2n2.techheaven.cart.controller;

import com.icebear2n2.techheaven.cart.service.CartItemService;
import com.icebear2n2.techheaven.domain.request.CartIDRequest;
import com.icebear2n2.techheaven.domain.request.CartItemRequest;
import com.icebear2n2.techheaven.domain.request.UpdateCartItemQuantityRequest;
import com.icebear2n2.techheaven.domain.response.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/item")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemResponse> addCart(@RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse cartItemResponse = cartItemService.addCart(cartItemRequest);

        if (cartItemResponse.isSuccess()) {
            return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(cartItemResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<CartItemResponse.CartItemData>> getCartItems(
            @RequestBody CartIDRequest cartIDRequest,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        PageRequest request = PageRequest.of(page, size);
        return new ResponseEntity<>(cartItemService.getAllByCart(cartIDRequest, request), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(@RequestBody UpdateCartItemQuantityRequest updateCartItemQuantityRequest) {
        CartItemResponse cartItemResponse = cartItemService.updateCartItemQuantity(updateCartItemQuantityRequest);
        if (cartItemResponse.isSuccess()) {
            return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cartItemResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeCartItem(@RequestBody Long cartItemId) {
        cartItemService.removeCartItem(cartItemId);
        return new ResponseEntity<>("Cart item removed successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> removeCartItemAll(@RequestBody Long cartId) {
        cartItemService.removeCartItemAll(cartId);
        return new ResponseEntity<>("All cart item removed successfully.", HttpStatus.OK);
    }
}

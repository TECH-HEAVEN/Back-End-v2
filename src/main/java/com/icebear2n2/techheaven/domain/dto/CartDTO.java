package com.icebear2n2.techheaven.domain.dto;

import com.icebear2n2.techheaven.domain.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private Long userId;
    private List<CartItemDTO> cartItems;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public CartDTO(Cart cart) {
        this.cartId = cart.getCartId();
        this.userId = cart.getUser().getUserId();
        this.cartItems = cart.getCartItems() != null ? cart.getCartItems().stream().map(CartItemDTO::new).toList() : null;
        this.createdAt = cart.getCreatedAt();
        this.updatedAt = cart.getUpdatedAt();
    }
}
package com.icebear2n2.techheaven.domain.dto;
import com.icebear2n2.techheaven.domain.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private Long cartId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public CartItemDTO(CartItem cartItem) {
        this.cartItemId = cartItem.getCartItemId();
        this.cartId = cartItem.getCartItemId();
        this.orderId = cartItem.getCartItemId();
        this.productId = cartItem.getProduct().getProductId();
        this.quantity = cartItem.getQuantity();
        this.createdAt = cartItem.getCreatedAt();
        this.updatedAt = cartItem.getUpdatedAt();
    }
}
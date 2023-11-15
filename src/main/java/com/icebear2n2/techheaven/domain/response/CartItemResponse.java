package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private boolean success;
    private String message;
    private CartItemData cartItemData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartItemData {
        private Long cartItemId;
        private Long cartId;
        private Long productId;
        private Integer quantity;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public CartItemData(CartItem cartItem) {
            this.cartItemId = cartItem.getCartItemId();
            this.cartId = cartItem.getCart().getCartId();
            this.productId = cartItem.getProduct().getProductId();
            this.quantity = cartItem.getQuantity();
            this.createdAt = cartItem.getCreatedAt();
            this.updatedAt = cartItem.getUpdatedAt();
        }
    }

    public static CartItemResponse success(CartItem cartItem) {
        return new CartItemResponse(true, "Success", new CartItemData(cartItem));
    }

    public static CartItemResponse failure(String message) {
        return new CartItemResponse(false, message, null);
    }
}

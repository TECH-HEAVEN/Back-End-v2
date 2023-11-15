package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.dto.CartItemDTO;
import com.icebear2n2.techheaven.domain.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private boolean success;
    private String message;
    private CartData cartData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartData {
        private Long cartId;
        private Long userId;
        private List<CartItemDTO> cartItems;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public CartData(Cart cart) {
            this.cartId = cart.getCartId();
            this.userId = cart.getUser().getUserId();
            this.cartItems = cart.getCartItems() != null ? cart.getCartItems().stream().map(CartItemDTO::new).toList() : null;
            this.createdAt = cart.getCreatedAt();
            this.updatedAt = cart.getUpdatedAt();
        }
    }

    public static CartResponse success(Cart cart) {
        return new CartResponse(true, "Success", new CartData(cart));
    }

    public static CartResponse failure(String message) { return  new CartResponse(false, message, null);}
}

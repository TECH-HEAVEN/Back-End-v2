package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemQuantityRequest {
    private Long cartId;
    private Long cartItemId;
    private Integer quantity;


    public void updateCartItemIfNotNull(CartItem cartItem) {
        if (this.quantity != null) {
            cartItem.setQuantity(this.quantity);
        }
    }
}

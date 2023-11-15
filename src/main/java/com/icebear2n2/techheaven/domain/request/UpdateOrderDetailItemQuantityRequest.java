package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDetailItemQuantityRequest {
    private Long orderDetailId;
    private Long orderId;
    private Long productId;
    private Integer quantity;

    public void updateOrderDetailItemIfNotNull(OrderDetail orderDetail) {
        if (this.quantity != null) {
            orderDetail.setQuantity(this.quantity);
        }
    }
}

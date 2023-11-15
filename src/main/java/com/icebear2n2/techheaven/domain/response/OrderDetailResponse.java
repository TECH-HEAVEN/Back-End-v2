package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private boolean success;
    private String message;
    private OrderDetailData orderDetailData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailData {
        private Long orderDetailId;
        private Long orderId;
        private Long productId;
        private Integer quantity;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Timestamp deletedAt;

        public OrderDetailData(OrderDetail orderDetail) {
            this.orderDetailId = orderDetail.getOrderDetailId();
            this.orderId = orderDetail.getOrder().getOrderId();
            this.productId = orderDetail.getProduct().getProductId();
            this.quantity = orderDetail.getQuantity();
            this.createdAt = orderDetail.getCreatedAt();
            this.updatedAt = orderDetail.getUpdatedAt();
            this.deletedAt = orderDetail.getDeletedAt();
        }
    }

    public static OrderDetailResponse success(OrderDetail orderDetail) {
        return new OrderDetailResponse(true, "Success", new OrderDetailData(orderDetail));
    }

    public static OrderDetailResponse failure(String message) { return new OrderDetailResponse(false, message, null); }
}

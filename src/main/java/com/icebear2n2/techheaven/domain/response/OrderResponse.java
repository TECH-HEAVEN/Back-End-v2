package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.dto.CartItemDTO;
import com.icebear2n2.techheaven.domain.dto.OrderDetailDTO;
import com.icebear2n2.techheaven.domain.entity.Order;
import com.icebear2n2.techheaven.domain.entity.OrderStatus;
import com.icebear2n2.techheaven.domain.entity.User;
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
public class OrderResponse {

    private boolean success;
    private String message;
    private OrderData orderData;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderData {
        private Long orderId;
        private Long trackingNumber;
        private User user;
        private List<CartItemDTO> cartItems;
        private Integer totalAmount;
        private OrderStatus status;
        private List<OrderDetailDTO> orderDetails;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Timestamp deletedAt;

        public OrderData(Order order) {
            this.orderId = order.getOrderId();
            this.trackingNumber = order.getTrackingNumber();
            this.user = order.getUser();
            this.cartItems = order.getCartItems().stream().map(CartItemDTO::new).toList();
            this.totalAmount = order.getTotalAmount();
            this.status = order.getStatus();
            this.orderDetails = order.getOrderDetails() != null ? order.getOrderDetails().stream().map(OrderDetailDTO::new).toList() : null;
            this.createdAt = order.getCreatedAt();
            this.updatedAt = order.getUpdatedAt();
            this.deletedAt = order.getDeletedAt();
        }
    }

    public static OrderResponse success(Order order) {
        return new OrderResponse(true, "Success", new OrderData(order));
    }

    public static OrderResponse failure(String message) { return new OrderResponse(false, message, null); }
}

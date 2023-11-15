package com.icebear2n2.techheaven.domain.dto;
import com.icebear2n2.techheaven.domain.entity.Order;
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
public class OrderDTO {
    private Long orderId;
    private Long trackingNumber;
    private Long userId;
    private List<CartItemDTO> cartItems;
    private Integer totalAmount;
    private String status;
    private List<OrderDetailDTO> orderDetails;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.trackingNumber = order.getTrackingNumber();
        this.userId = order.getUser().getUserId();
        this.cartItems = order.getCartItems().stream().map(CartItemDTO::new).toList();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus().name();
        this.orderDetails = order.getOrderDetails().stream().map(OrderDetailDTO::new).toList();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.deletedAt = order.getDeletedAt();
    }
}
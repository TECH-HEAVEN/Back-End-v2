package com.icebear2n2.techheaven.domain.dto;
import com.icebear2n2.techheaven.domain.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long orderDetailId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp updatedAt;

    public OrderDetailDTO(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getOrderDetailId();
        this.orderId = orderDetail.getOrder().getOrderId();
        this.productId = orderDetail.getProduct().getProductId();
        this.quantity = orderDetail.getQuantity();
        this.createdAt = orderDetail.getCreatedAt();
        this.deletedAt = orderDetail.getDeletedAt();
        this.updatedAt = orderDetail.getUpdatedAt();
    }
}
package com.icebear2n2.techheaven.domain.dto;

import com.icebear2n2.techheaven.domain.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long deliveryId;
    private Long userId;
    private Long orderId;
    private String status;
    private Timestamp estimatedArrival;
    private Timestamp actualArrival;
    private String courierName;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public DeliveryDTO(Delivery delivery) {
        this.deliveryId = delivery.getDeliveryId();
        this.userId = delivery.getUser().getUserId();
        this.orderId = delivery.getOrder().getOrderId();
        this.status = delivery.getStatus().name();
        this.estimatedArrival = delivery.getEstimatedArrival();
        this.actualArrival = delivery.getActualArrival();
        this.courierName = delivery.getCourierName();
        this.createdAt = delivery.getCreatedAt();
        this.updatedAt = delivery.getUpdatedAt();
    }
}

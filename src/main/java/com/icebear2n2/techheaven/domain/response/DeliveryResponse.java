package com.icebear2n2.techheaven.domain.response;

import com.icebear2n2.techheaven.domain.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {

    private boolean success;
    private String message;
    private DeliveryData deliveryData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeliveryData {
        private Long deliveryId;
        private Long userId;
        private Long orderId;
        private String status;
        private Timestamp estimatedArrival;
        private Timestamp actualArrival;
        private String courierName;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public DeliveryData(Delivery delivery) {
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
    public static DeliveryResponse success(Delivery delivery) {
        return new DeliveryResponse(true, "Success", new DeliveryData(delivery));
    }

    public static DeliveryResponse failure(String message) { return new DeliveryResponse(false, message, null); }
}

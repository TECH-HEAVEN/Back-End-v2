package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Delivery;
import com.icebear2n2.techheaven.domain.entity.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDeliveryStatusRequest {
    private Long deliveryId;
    private DeliveryStatus status;
    private Timestamp actualArrival;

    public void updateDeliveryIfNotNull(Delivery delivery) {
        if (this.status != null) {
            delivery.setStatus(this.status);
        }

        if (this.actualArrival != null) {
            delivery.setActualArrival(this.actualArrival);
        }
    }
}

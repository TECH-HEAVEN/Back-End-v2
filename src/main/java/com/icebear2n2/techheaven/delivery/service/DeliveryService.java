package com.icebear2n2.techheaven.delivery.service;

import com.icebear2n2.techheaven.domain.entity.Delivery;
import com.icebear2n2.techheaven.domain.entity.Order;
import com.icebear2n2.techheaven.domain.entity.User;
import com.icebear2n2.techheaven.domain.repository.DeliveryRepository;
import com.icebear2n2.techheaven.domain.repository.OrderRepository;
import com.icebear2n2.techheaven.domain.repository.UserRepository;
import com.icebear2n2.techheaven.domain.request.DeliveryRequest;
import com.icebear2n2.techheaven.domain.request.UpdateDeliveryStatusRequest;
import com.icebear2n2.techheaven.domain.response.DeliveryResponse;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import com.icebear2n2.techheaven.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {
        User user = userRepository.findById(deliveryRequest.getUserId()).orElseThrow(() -> new TechHeavenException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findById(deliveryRequest.getOrderId()).orElseThrow(() -> new TechHeavenException(ErrorCode.ORDER_NOT_FOUND));
        Delivery delivery = deliveryRequest.toEntity(user, order);
        Delivery saveDelivery = deliveryRepository.save(delivery);

        return DeliveryResponse.success(saveDelivery);
    }

    public DeliveryResponse updateDeliveryStatus(UpdateDeliveryStatusRequest updateDeliveryStatusRequest) {
        if (!deliveryRepository.existsByDeliveryId(updateDeliveryStatusRequest.getDeliveryId())) {
            DeliveryResponse.failure(ErrorCode.DELIVERY_NOT_FOUND.toString());
        }

        try {
            Delivery delivery = deliveryRepository.findById(updateDeliveryStatusRequest.getDeliveryId()).orElseThrow(() -> new TechHeavenException(ErrorCode.DELIVERY_NOT_FOUND));
            updateDeliveryStatusRequest.updateDeliveryIfNotNull(delivery);
            deliveryRepository.save(delivery);
            return DeliveryResponse.success(delivery);
        } catch (Exception e) {
            return DeliveryResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }
}

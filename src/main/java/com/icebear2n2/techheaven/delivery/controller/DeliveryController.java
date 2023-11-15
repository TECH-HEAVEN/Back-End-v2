package com.icebear2n2.techheaven.delivery.controller;

import com.icebear2n2.techheaven.delivery.service.DeliveryService;
import com.icebear2n2.techheaven.domain.request.DeliveryRequest;
import com.icebear2n2.techheaven.domain.request.UpdateDeliveryStatusRequest;
import com.icebear2n2.techheaven.domain.response.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody DeliveryRequest deliveryRequest) {
        DeliveryResponse deliveryResponse = deliveryService.createDelivery(deliveryRequest);

        if (deliveryResponse.isSuccess()) {
            return new ResponseEntity<>(deliveryResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(deliveryResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(@RequestBody UpdateDeliveryStatusRequest updateDeliveryStatusRequest) {
        DeliveryResponse deliveryResponse = deliveryService.updateDeliveryStatus(updateDeliveryStatusRequest);

        if (deliveryResponse.isSuccess()) {
            return new ResponseEntity<>(deliveryResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deliveryResponse, HttpStatus.BAD_REQUEST);
        }
    }
}

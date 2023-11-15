package com.icebear2n2.techheaven.order.service;

import com.icebear2n2.techheaven.domain.entity.Order;
import com.icebear2n2.techheaven.domain.entity.OrderDetail;
import com.icebear2n2.techheaven.domain.entity.Product;
import com.icebear2n2.techheaven.domain.repository.OrderDetailRepository;
import com.icebear2n2.techheaven.domain.repository.OrderRepository;
import com.icebear2n2.techheaven.domain.repository.ProductRepository;
import com.icebear2n2.techheaven.domain.request.OrderDetailRequest;
import com.icebear2n2.techheaven.domain.request.OrderIDRequest;
import com.icebear2n2.techheaven.domain.request.UpdateOrderDetailItemQuantityRequest;
import com.icebear2n2.techheaven.domain.response.OrderDetailResponse;
import com.icebear2n2.techheaven.exception.ErrorCode;
import com.icebear2n2.techheaven.exception.TechHeavenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailService.class);
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderDetailResponse createOrderDetail(OrderDetailRequest orderDetailRequest) {
        Order order = orderRepository.findById(orderDetailRequest.getOrderId())
                .orElseThrow(() -> new TechHeavenException(ErrorCode.ORDER_NOT_FOUND));
        Product product = productRepository.findById(orderDetailRequest.getProductId()).orElseThrow(() -> new TechHeavenException(ErrorCode.PRODUCT_NOT_FOUND));
        try {
            OrderDetail orderDetail = orderDetailRequest.toEntity(order, product);
            OrderDetail saveOrderDetail = orderDetailRepository.save(orderDetail);


            return OrderDetailResponse.success(saveOrderDetail);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return OrderDetailResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }

    }

    public Page<OrderDetailResponse.OrderDetailData> getAllByOrder(OrderIDRequest orderIDRequest, PageRequest pageRequest) {
        Order order = orderRepository.findById(orderIDRequest.getOrderId())
                .orElseThrow(() -> new TechHeavenException(ErrorCode.ORDER_NOT_FOUND));

        Page<OrderDetail> all = orderDetailRepository.findAllByOrder(order, pageRequest);
        return all.map(OrderDetailResponse.OrderDetailData::new);
    }

    public OrderDetailResponse updateOrderDetailItemQuantity(UpdateOrderDetailItemQuantityRequest updateOrderDetailItemQuantityRequest) {
        Order order = orderRepository.findById(updateOrderDetailItemQuantityRequest.getOrderId())
                .orElseThrow(() -> new TechHeavenException(ErrorCode.ORDER_NOT_FOUND));

        try {
            OrderDetail orderDetail = order.getOrderDetails().stream()
                    .filter(detail -> detail.getProduct().getProductId().equals(updateOrderDetailItemQuantityRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new TechHeavenException(ErrorCode.ORDER_DETAIL_NOT_FOUND));



            if (updateOrderDetailItemQuantityRequest.getQuantity() != null) {
                orderDetail.setQuantity(updateOrderDetailItemQuantityRequest.getQuantity());
                OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
                return OrderDetailResponse.success(updatedOrderDetail);
            } else {
                return OrderDetailResponse.failure(ErrorCode.INVALID_QUANTITY.toString());
            }
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return OrderDetailResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }
}


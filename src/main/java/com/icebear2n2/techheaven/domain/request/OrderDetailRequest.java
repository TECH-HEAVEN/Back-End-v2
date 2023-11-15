package com.icebear2n2.techheaven.domain.request;

import com.icebear2n2.techheaven.domain.entity.Order;
import com.icebear2n2.techheaven.domain.entity.OrderDetail;
import com.icebear2n2.techheaven.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private Long orderId;
    private Long productId;
    private Integer quantity;

    public OrderDetail toEntity(Order order, Product product) {
        return OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .build();
    }
}

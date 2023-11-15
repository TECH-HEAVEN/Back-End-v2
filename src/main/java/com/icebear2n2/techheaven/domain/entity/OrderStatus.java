package com.icebear2n2.techheaven.domain.entity;

public enum OrderStatus {
    PENDING,  // 주문 진행 중
    SHIPPED,  // 배송 중
    DELIVERED, // 배송 완료
    COMPLETED, // 주문 완료됨
    CANCELED  // 주문 취소됨
}

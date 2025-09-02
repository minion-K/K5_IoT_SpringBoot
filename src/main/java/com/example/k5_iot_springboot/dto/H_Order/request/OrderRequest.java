package com.example.k5_iot_springboot.dto.H_Order.request;

import com.example.k5_iot_springboot.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    /** 주문 생성 요청 DTO */
    public record OrderCreateRequest(
            List<OrderItemLine> items
    ) {}

    /** 주문 항목(라인) - 생성 요청용 */
    public record OrderItemLine(
            Long productId,
            int quantity
    ) {}

    /** 주문 조회 조건 DTO */
    public record OrderSearchCondition(
            Long userId,
            OrderStatus orderStatus,
            LocalDateTime from,
            LocalDateTime to
    ) {}
}

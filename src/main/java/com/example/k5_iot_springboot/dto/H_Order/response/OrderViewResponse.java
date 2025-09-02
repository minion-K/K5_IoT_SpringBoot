package com.example.k5_iot_springboot.dto.H_Order.response;

public class OrderViewResponse {
    public record OrderSummary(
            Long orderId,
            Long userId,
            String orderStatus,
            String productName,
            Integer quantity,
            Integer price,
            Integer totalPrice,
            String orderedAt
    ) {}

    public record OrderTotalsRowDto(
            Long orderId,
            Long userId,
            String orderStatus,
            Integer orderTotalAmount,
            Long orderTotalQty,
            String orderedAt
    ) {}
}

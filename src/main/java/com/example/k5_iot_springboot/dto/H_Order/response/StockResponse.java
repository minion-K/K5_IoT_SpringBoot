package com.example.k5_iot_springboot.dto.H_Order.response;

public class StockResponse {
    /** 재고 응답 DTO */
    public record Response(
            Long productId,
            int quantity
    ) {}
}

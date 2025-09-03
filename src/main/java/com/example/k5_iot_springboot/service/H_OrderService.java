package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.common.enums.OrderStatus;
import com.example.k5_iot_springboot.dto.H_Order.request.OrderRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.OrderResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

public interface H_OrderService {
    ResponseDto<OrderResponse.Detail> create(OrderRequest.OrderCreateRequest request);
    ResponseDto<OrderResponse.Detail> approve(UserPrincipal principal, Long orderId);
    ResponseDto<OrderResponse.Detail> cancel(UserPrincipal principal, Long orderId);
    ResponseDto<List<OrderResponse.Detail>> search(UserPrincipal principal, Long userId, OrderStatus status, LocalDateTime from, LocalDateTime to);
}

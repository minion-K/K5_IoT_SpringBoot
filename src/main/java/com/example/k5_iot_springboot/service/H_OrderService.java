package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.H_Order.request.OrderRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.OrderResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import jakarta.validation.Valid;

public interface H_OrderService {
    ResponseDto<OrderResponse.Detail> create(OrderRequest.@Valid OrderCreateRequest request);
    ResponseDto<OrderResponse.Detail> approve(UserPrincipal principal, Long orderId);
    ResponseDto<OrderResponse.Detail> cancel(UserPrincipal principal, Long orderId);
}

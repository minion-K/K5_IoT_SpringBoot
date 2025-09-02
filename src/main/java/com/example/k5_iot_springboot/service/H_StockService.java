package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.H_Order.request.StockRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.StockResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import jakarta.validation.Valid;

public interface H_StockService {
    ResponseDto<StockResponse.Response> adjust(UserPrincipal principal, StockRequest.@Valid StockAdjust req);
    ResponseDto<StockResponse.Response> set(UserPrincipal principal, StockRequest.@Valid StockSet req);
    ResponseDto<StockResponse.Response> getByProductId(Long productId);
}

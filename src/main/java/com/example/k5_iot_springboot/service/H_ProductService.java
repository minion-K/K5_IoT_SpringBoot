package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.H_Order.request.ProductRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.ProductResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import jakarta.validation.Valid;

public interface H_ProductService {

    ResponseDto<ProductResponse.DetailResponse> create(UserPrincipal principal, @Valid ProductRequest.Create request);
    ResponseDto<ProductResponse.DetailResponse> update(Long productId, UserPrincipal principal, ProductRequest.@Valid Update request);
    ResponseDto<ProductResponse.DetailResponse> getProductById(Long productId);
}

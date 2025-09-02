package com.example.k5_iot_springboot.controller;

import com.example.k5_iot_springboot.common.constants.APIMappingPattern;
import com.example.k5_iot_springboot.dto.E_Board.response.BoardResponseDto;
import com.example.k5_iot_springboot.dto.H_Order.request.ProductRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.ProductResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.H_ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 제품 등록/수정/조회
 * : 권한 'USER', 'MANAGER', 'ADMIN' 중 (등록, 수정)은 ADMIN만 (조회)는 누구나 가능
 * */

@RestController
@RequestMapping(APIMappingPattern.Products.ROOT)
@RequiredArgsConstructor
public class H_ProductController {
    private final H_ProductService productService;
    
//    제품 등록 
    @PostMapping
    public ResponseEntity<ResponseDto<ProductResponse.DetailResponse>> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ProductRequest.Create request
            ) {
        ResponseDto<ProductResponse.DetailResponse> response = productService.create(principal, request);
        return ResponseEntity.ok(response);
    }
//    제품 수정
    @PutMapping(APIMappingPattern.Products.ID_ONLY)
    public ResponseEntity<ResponseDto<ProductResponse.DetailResponse>> update(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ProductRequest.Update request
            ) {
        ResponseDto<ProductResponse.DetailResponse> response = productService.update(productId,principal, request);
        return ResponseEntity.ok(response);
    }
//    제품 조회
    @GetMapping(APIMappingPattern.Products.ID_ONLY)
    public ResponseEntity<ResponseDto<ProductResponse.DetailResponse>> getProductById(
            @PathVariable Long productId
    ) {
        ResponseDto<ProductResponse.DetailResponse> response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }
}

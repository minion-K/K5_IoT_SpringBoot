package com.example.k5_iot_springboot.controller;

import com.example.k5_iot_springboot.common.constants.APIMappingPattern;
import com.example.k5_iot_springboot.dto.H_Order.request.StockRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.StockResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.H_StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 재고 증강/설정/조회
 * */

@RestController
@RequestMapping(APIMappingPattern.Stocks.ROOT)
@RequiredArgsConstructor
public class H_StockController {
    private final H_StockService stockService;

//    증감식 연산 - 현재 재고에 delta 만큼 더하거나 빼는 연산
//    예) 입고(+10), 출고(-4), 반품(+1) 등 이벤트형 변경
    @PostMapping(APIMappingPattern.Stocks.ADJUST)
    public ResponseEntity<ResponseDto<StockResponse.Response>> adjust(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody StockRequest.StockAdjust req
            ) {
        ResponseDto<StockResponse.Response> response = stockService.adjust(principal, req);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<StockResponse.Response>> set(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody StockRequest.StockSet req
    ) {
        ResponseDto<StockResponse.Response> response = stockService.set(principal, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping(APIMappingPattern.Stocks.PRODUCT_ID)
    public ResponseEntity<ResponseDto<StockResponse.Response>> getByProductId(
            @PathVariable Long productId
    ) {
        ResponseDto<StockResponse.Response> response = stockService.getByProductId(productId);
        return ResponseEntity.ok(response);
    }
}

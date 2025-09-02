package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.dto.H_Order.request.StockRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.StockResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.entity.H_Stock;
import com.example.k5_iot_springboot.repository.H_StockRepository;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.H_StockService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class H_StockServiceImpl implements H_StockService {
    private final H_StockRepository stockRepository;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseDto<StockResponse.Response> adjust(UserPrincipal principal, StockRequest.@Valid StockAdjust req) {
//        재고 증감 (delta)
//        : delta 값이 양수 - 입고/반품
//                값이 음수 - 출고/차감
        StockResponse.Response data = null; // 실제 ResponseDto 내부에서 전달될 데이터 타입을 초기화

        H_Stock stock = stockRepository.findByProductIdForUpdate(req.productId())
                .orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾을 수 없습니다. productId:" + req.productId()));

        int newQuantity = stock.getQuantity() + req.delta();

        if(newQuantity < 0) throw new IllegalArgumentException("재고가 부족합니다");

        stock.setQuantity(newQuantity);

        data = new StockResponse.Response(
                stock.getProduct().getId(),
                stock.getQuantity()
        );
        return ResponseDto.setSuccess("재고가 성공적으로 증갑되었습니다.", data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseDto<StockResponse.Response> set(UserPrincipal principal, StockRequest.@Valid StockSet req) {
        StockResponse.Response data = null;

        H_Stock stock = stockRepository.findByProductIdForUpdate(req.productId())
                .orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾을 수 없습니다. productId:" + req.productId()));

        if(req.quantity() < 0) throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        stock.setQuantity(req.quantity());

        data = new StockResponse.Response(
                stock.getProduct().getId(),
                stock.getQuantity()
        );

        return ResponseDto.setSuccess("재고가 성공적으로 설정되었습니다.", data);
    }

    @Override
    public ResponseDto<StockResponse.Response> getByProductId(Long productId) {
        StockResponse.Response data = null;

        H_Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("재고 정보를 찾을 수 없습니다. productId:" + productId));

        data = new StockResponse.Response(productId, stock.getQuantity());
        return ResponseDto.setSuccess("재고가 성공적으로 조회되었습니다.", data);
    }
}

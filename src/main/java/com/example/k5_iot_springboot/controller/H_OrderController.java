package com.example.k5_iot_springboot.controller;

import com.example.k5_iot_springboot.dto.H_Order.request.OrderRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.OrderResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.entity.H_Order;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.H_OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.metamodel.mapping.ordering.ast.PathConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// === Controller 기본 어노테이션
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
/**
 * 주문 생성/승인/취소 + 검색
 * */
public class H_OrderController {
    private final H_OrderService orderService; // 생성자 주입

    /** 주문 생성: 인증 주체의 userId를 사용 (POST): 새로운 데이터 생성 */
    @PostMapping
//    cf) ResponseEntity(HttpStatus 상태코드, HttpHeaders 요청/응답에 대한 요구사항, HttpBody 응답 본문)
//        ResponseDto(HttpBody 응답 본문 타입) - 데이터 전송 객체
//          >> result(boolean), message(String), data(T): 실제 요청 데이터 타입
    public ResponseEntity<ResponseDto<OrderResponse.Detail>> create(
//            매개변수 - Controller (@PathVariable, @RequestBody, @RequestParam)
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody OrderRequest.OrderCreateRequest request
    ) {
        ResponseDto<OrderResponse.Detail> response = orderService.create(request);
//        return ResponseEntity.ok(response);
        return ResponseEntity.ok().body(response); // 클라이언트에 전달할 정보가 있을 경우에 사용
    }

    /** 주문 승인 ADMIN/MANAGER만 가능 */
    @PostMapping("/{orderId}/approve")
    public ResponseEntity<ResponseDto<OrderResponse.Detail>> approve(
            @AuthenticationPrincipal UserPrincipal principal, // 주문 승인자 정보를 저장(활용)할 경우
            @PathVariable Long orderId
    ) {
        ResponseDto<OrderResponse.Detail> response = orderService.approve(principal, orderId);
        return ResponseEntity.ok(response);
    }

    /** 주문 취소: 대기 상태일테만 취소 가능 */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ResponseDto<OrderResponse.Detail>> cancel(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable Long orderId
    ) {

    }
}

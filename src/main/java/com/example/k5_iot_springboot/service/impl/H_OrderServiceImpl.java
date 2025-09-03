package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.common.enums.OrderStatus;
import com.example.k5_iot_springboot.common.utils.DateUtils;
import com.example.k5_iot_springboot.dto.H_Order.request.OrderRequest;
import com.example.k5_iot_springboot.dto.H_Order.response.OrderResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.entity.F_User;
import com.example.k5_iot_springboot.entity.H_Order;
import com.example.k5_iot_springboot.entity.H_OrderItem;
import com.example.k5_iot_springboot.entity.H_Product;
import com.example.k5_iot_springboot.repository.H_OrderRepository;
import com.example.k5_iot_springboot.repository.H_ProductRepository;
import com.example.k5_iot_springboot.repository.H_StockRepository;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.H_OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// 인터페이스의 추상 메서드를 Impl 클래스 파일에서 "강제 구현"
@Service
@RequiredArgsConstructor // final 필드 Or @NonNull 필드만을 매개변수로 가지는 생성자
@Transactional(readOnly = true)
public class H_OrderServiceImpl implements H_OrderService {
    private final EntityManager em; // 사용자 참조 - getReference 등
    private final H_OrderRepository orderRepository;
    private final H_ProductRepository productRepository;
    private final H_StockRepository stockRepository;

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ResponseDto<OrderResponse.Detail> create(UserPrincipal principal,OrderRequest.OrderCreateRequest request) {
        OrderResponse.Detail data = null;

        if(request.items() == null || request.items().isEmpty())
            throw new IllegalArgumentException("주문 항목이 비어있습니다. ");

//        principal에서 userId 추출
        Long authUserId = principal.getId();

//        EntityManager.getReference() VS JPA.findById()
//        1) EntityManager.getReference()
//              : 단순히 연관 관계 주입만 필요할 때 사용
//              - 실제 SQL SELECT 문을 실행하지 않고 프록시 객체를 반환
//              >> 어차피 Order 엔티티의 user를 참조하는 데 실제 user의 다른 필드가 필요 없는 경우 효율적
//        2) UserRepository.findById()
//              : DB 조회 쿼리를 날리고 F_User 엔티티를 반환
//              - 존재하지 않는 userId이면 예외를 던지고 싶을 때 사용(안전성)

//        인증 주체 userId로 F_User 프록시(대리인, 중계자) 획득(UserRepository 없이도 가능)
        F_User userRef = em.getReference(F_User.class, authUserId);

        H_Order order = H_Order.builder()
                .user(userRef)
                .orderStatus(OrderStatus.PENDING) // 기본값
                .build();

        for(OrderRequest.OrderItemLine line: request.items()) {
            if(line.quantity() <= 0) throw new IllegalArgumentException("수량은 1이상이어야 합니다.");
            H_Product product = productRepository.findById(line.productId())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. PRODUCT_ID: " + line.productId()));
            H_OrderItem item = H_OrderItem.builder()
                    .product(product)
                    .quantity(line.quantity())
                    .build();
            order.addItem(item);
        }
        H_Order saved = orderRepository.save(order);

        data = toOrderResponse(saved);

        return ResponseDto.setSuccess("주문이 성공적으로 등록되었습니다", data);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseDto<OrderResponse.Detail> approve(UserPrincipal principal, Long orderId) {
        return null;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @authz.canCancel(#orderId, authentication)")
    public ResponseDto<OrderResponse.Detail> cancel(UserPrincipal principal, Long orderId) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') or @authz.isSelf(#principal.id, authentication)")
    public ResponseDto<List<OrderResponse.Detail>> search(UserPrincipal principal, Long userId, OrderStatus status, LocalDateTime from, LocalDateTime to) {
        return null;
    }

//    ===== 변환 유틸 =====
    private OrderResponse.Detail toOrderResponse(H_Order order) {
//        각 주문 항목 변환
        List<OrderResponse.OrderItemList> items = order.getItems().stream()
                .map(item -> {
                    int price = item.getProduct().getPrice();
                    int quantity = item.getQuantity();
                    int lineTotal = (int) price * quantity;

                    return new OrderResponse.OrderItemList(
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            price,
                            quantity,
                            lineTotal
                    );
                }).toList();
//        총액 계산 (long)
        int totalAmount = items.stream()
                .mapToInt(OrderResponse.OrderItemList::lineTotal)
                .sum();

//        총 수량 계산
        int totalQuantity = items.stream()
                .mapToInt(OrderResponse.OrderItemList::quantity)
                .sum();

        return new OrderResponse.Detail(
                order.getId(),
                order.getUser().getId(),
                order.getOrderStatus(),
                totalAmount,
                totalQuantity,
                DateUtils.toKstString(order.getCreatedAt()),
                items
        );
    }
}

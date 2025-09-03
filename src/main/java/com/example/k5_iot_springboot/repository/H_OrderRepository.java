package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.common.enums.OrderStatus;
import com.example.k5_iot_springboot.entity.H_Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// 인터페이스 간의 확장
@Repository
public interface H_OrderRepository extends JpaRepository<H_Order, Long>, H_OrderRepositoryCustom {

    /** 주문 상세 (주문 - 항목 - 상품) fetch join 단건 조회 */
    @Query("""
        select distinct o from H_Order o
            left join fetch o.items oi
            left join fetch oi.product p
        where o.id = :orderId
    """)
    Optional<H_Order> findDetailById(@Param("orderId") Long orderId);
}

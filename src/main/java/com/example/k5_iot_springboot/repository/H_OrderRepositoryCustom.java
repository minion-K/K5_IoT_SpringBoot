package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.common.enums.OrderStatus;
import com.example.k5_iot_springboot.entity.H_Order;

import java.time.LocalDateTime;
import java.util.List;

public interface H_OrderRepositoryCustom {

    /** 유저/상태/기간 조건에 따른 주문 목록 조회 */
    List<H_Order> searchOrders(Long userId, OrderStatus status, LocalDateTime from, LocalDateTime to);
}

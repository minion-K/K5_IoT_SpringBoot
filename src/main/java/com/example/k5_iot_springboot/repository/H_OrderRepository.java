package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.H_Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 인터페이스 간의 확장
@Repository
public interface H_OrderRepository extends JpaRepository<H_Order, Long> {
}

package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.H_Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface H_StockRepository extends JpaRepository<H_Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    비관적 잠금(재고 경합 제어를 위함)
//    : 데이터를 수정하기 전에 해당 데이터에 대한 다른 트랜잭션의 접근을 막아 데이터의 정합성을 보장
//    : 읽기 및 쓰기 모두 막는 역할
    @Query("select s from H_Stock s where s.product.id = :productId")
    Optional<H_Stock> findByProductIdForUpdate(@Param("productId") Long productId);

    Optional<H_Stock> findByProductId(Long productId);
}

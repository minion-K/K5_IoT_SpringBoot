package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.H_Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H_StockRepository extends JpaRepository<H_Stock, Long> {
}

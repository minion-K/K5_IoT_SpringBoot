package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTruckId(Long TruckId);
}

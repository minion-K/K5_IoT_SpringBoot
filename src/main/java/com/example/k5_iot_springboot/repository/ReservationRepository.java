package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.Reservation;
import com.example.k5_iot_springboot.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTruckId(Long TruckId);

    List<Reservation> truck(Truck truck);

    Optional<Reservation> findByIdAndTruckId(Long reservationId, Long truckId);
}

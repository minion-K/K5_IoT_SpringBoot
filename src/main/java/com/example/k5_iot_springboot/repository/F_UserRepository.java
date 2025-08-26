package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.F_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface F_UserRepository extends JpaRepository<F_User, Long> {
    Optional<F_User> findByLoginId(String loginId);
}

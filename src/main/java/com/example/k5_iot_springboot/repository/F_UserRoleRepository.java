package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.F_UserRole;
import com.example.k5_iot_springboot.entity.F_UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface F_UserRoleRepository extends JpaRepository<F_UserRole, F_UserRoleId> {
    List<F_UserRole> findByIdUserId(Long userId);
}

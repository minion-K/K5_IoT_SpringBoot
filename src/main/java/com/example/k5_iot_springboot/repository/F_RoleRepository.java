package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.common.enums.RoleType;
import com.example.k5_iot_springboot.entity.F_Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface F_RoleRepository extends JpaRepository<F_Role, RoleType> {
}

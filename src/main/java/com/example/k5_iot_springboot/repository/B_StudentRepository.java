package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.B_Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface B_StudentRepository extends JpaRepository<B_Student, Long> {
}

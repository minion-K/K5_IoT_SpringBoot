package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.E_Board;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface E_BoardRepository extends JpaRepository<E_Board, Long> {
//    Cursor 기반: id 기준 내림차순 " 더 작은 id"를 size 만큼 가져오기
    Slice<E_Board> findByIdLessThanOrderByIdDesc(long startId, Pageable pageable);
}

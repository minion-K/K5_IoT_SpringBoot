package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.common.enums.OrderStatus;
import com.example.k5_iot_springboot.entity.H_Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class H_OrderRepositoryImpl implements H_OrderRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<H_Order> searchOrders(
            Long userId, OrderStatus status, LocalDateTime from, LocalDateTime to
    ) {
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT o " +
                "FROM H_Order o " +
                    "LEFT JOIN FETCH o.items oi " +
                    "LEFT JOIN FETCH oi.product p " +
                "WHERE 1 = 1" // 항상 참이 되는 조건) 사실상 [ SELECT * FROM orders ] 와 동일한 결과
        );

        Map<String, Object> params = new HashMap<>();

        if(userId != null) {
            jpql.append(" and user.id = :userId");
            params.put("userId", userId);
        }

        if(status != null) {
            jpql.append(" and o.orderStatus = :status");
            params.put("status", status);
        }

        if(from != null) {
            jpql.append(" and o.createdAt >= :from");
            params.put("from", from);
        }

        if(to != null) {
            jpql.append(" and o.createdAt <= :to");
            params.put("to", to);
        }

        jpql.append(" order by o.createdAt desc, o.id desc");

//        명시적 타입 사용: TypedQuery
        TypedQuery<H_Order> query = em.createQuery(jpql.toString(), H_Order.class);
//        >> @Query 쓰일 쿼리문
        for(Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<H_Order> results = query.getResultList();

        return results;
    }
}

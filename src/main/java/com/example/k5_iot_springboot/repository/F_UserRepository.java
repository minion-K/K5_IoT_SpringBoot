package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.F_User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface F_UserRepository extends JpaRepository<F_User, Long> {
//    roles의 지연 로딩(LAZY)로 인한 LAZYInitializationException 위험
//    해결 방법 1) 레포지토리에 fetch-join 쿼리 추가
//    : u.roles 컬렉션을 한 번에 가져오기 때문에 N+1 문제 발생
    @Query("""
        SELECT u
        FROM F_User u
            LEFT JOIN FETCH u.roles
        WHERE u.loginId = :loginId    
    """)
    Optional<F_User> findWithRolesByLoginId(@Param("loginId") String loginId);

//    해결 방법 2) JPA의 @EntityGraph를 사용하여 fetch join을 자동으로 적용 방식
//    @EntityGraph: DATA JPA에서 fetch 조인을 어노테이션으로 대신하는 기능
    @EntityGraph(attributePaths = "roles")
    Optional<F_User> findByLoginId(String loginId);
    @EntityGraph(attributePaths = "roles")
    Optional<F_User> findWithRolesById(
            Long id);


    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}

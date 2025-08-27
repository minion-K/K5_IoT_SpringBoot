package com.example.k5_iot_springboot.security;

import com.example.k5_iot_springboot.entity.F_User;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * === UserPrincipalMapper ===
 * : 도메인 엔티티(F_User) -> 보안 표현(UserPrincipal)로 변환
 * +) 현재 F_User 에는 roles가 없으므로 기본 ROLE_USER 부여
 * 
 * >> 추후 역할/권한 도입 시 해당 클래스만 변경하면 전역 반영 가능
 *
 * cf) Spring Security는 인증/인가 단계에서 UserDetails 인터페이스를 사용 (>> UserPrincipal)
 *      - 본 Mapper는 영속 엔티티로부터 인증/인가에 꼭 필요한 값만 추출하여
 *          , 경량/불변 VO(Principal)로 만들어 SecurityContext에 안전하게 전달되도록 하는 Mapper
 *          
 * # 사용 위치 #
 * CustomUserDetailsService#loadUserByUsername(...)이 F_User 조회
 *  -> 본 Mapper로 UserPrincipal 생성
 *  -> Authentication(principal)에 주입되어 보안 컨텍스트에 저장
 * */

@Component
public class UserPrincipalMapper {
    @NonNull
    public UserPrincipal map(@NonNull F_User user) {
        Collection<SimpleGrantedAuthority> authorities
                = List.of(new SimpleGrantedAuthority("ROLE_USER"));

//        1).

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getLoginId())
                .password(user.getPassword())
                .authorities(authorities)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}

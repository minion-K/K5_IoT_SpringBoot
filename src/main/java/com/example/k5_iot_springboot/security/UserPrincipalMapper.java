package com.example.k5_iot_springboot.security;

import com.example.k5_iot_springboot.entity.F_User;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * === UserPrincipalMapper ===
 * : 엔티티(F_User) -> 보안 표현(UserPrincipal)로 변환
 * +) 현재 F_User 에는 roles가 없으므로 기본 ROLE_USER 부여
 * 
 * >> 추후 역할/권한 도입 시 해당 클래스만 변경하면 전역 반영 가능
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

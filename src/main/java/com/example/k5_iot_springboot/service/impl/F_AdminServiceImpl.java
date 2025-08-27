package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.dto.F_Auth.response.SignInResponse;
import com.example.k5_iot_springboot.dto.F_User.request.RoleModifyRequest;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.entity.F_User;
import com.example.k5_iot_springboot.repository.F_UserRepository;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.service.F_AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class F_AdminServiceImpl implements F_AdminService {
    private final F_UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto<Void> replaceRoles(UserPrincipal principal, RoleModifyRequest req) {

        return null;
    }

    @Override
    @Transactional
    public ResponseDto<SignInResponse> addRoles(UserPrincipal principal, RoleModifyRequest req) {
        F_User user = userRepository.findByLoginId(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다."));

        if(req.role() == null || user.getRoles().contains(req.role())) {
            throw new IllegalArgumentException("추가할 ROLE이 없거나 이미 존재하는 ROLE 입니다.");
        }
        user.getRoles().add(req.role());

        SignInResponse response = new SignInResponse(
                "",
                "",
                0,
                user.getLoginId(),
                user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );
        return ResponseDto.setSuccess("권한이 추가되었습니다.", response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> removeRoles(UserPrincipal principal, RoleModifyRequest req) {
        return null;
    }
}

package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.dto.F_User.request.UserProfileUpdateRequest;
import com.example.k5_iot_springboot.dto.F_User.response.UserProfileResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.entity.F_User;
import com.example.k5_iot_springboot.repository.F_UserRepository;
import com.example.k5_iot_springboot.security.UserPrincipal;
import com.example.k5_iot_springboot.security.util.PrincipalUtils;
import com.example.k5_iot_springboot.service.F_UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class F_UserServiceImpl implements F_UserService {
    private final F_UserRepository userRepository;

    @Override
    public ResponseDto<UserProfileResponse.MyPageResponse> getMyInfo(UserPrincipal principal) {
        PrincipalUtils.requiredActive(principal);

        F_User user = userRepository.findByLoginId(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 username의 사용자가 없습니다. [" + principal.getUsername() + " ]"));

        UserProfileResponse.MyPageResponse data
                = new UserProfileResponse.MyPageResponse(
                        user.getId(),
                        user.getLoginId(),
                        user.getEmail(),
                        user.getNickname(),
                        user.getGender()
        );

        return ResponseDto.setSuccess("SUCCESS", data);
    }

    @Override
    @Transactional
    public ResponseDto<UserProfileResponse.MyPageResponse> updateMyInfo(UserPrincipal principal, UserProfileUpdateRequest request) {
        PrincipalUtils.requiredActive(principal);

        F_User user = userRepository.findByLoginId(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("해당 username의 사용자가 없습니다. [" + principal.getUsername() + " ]"));

        user.changeProfile(request.nickname(), request.gender());
        userRepository.flush();

        UserProfileResponse.MyPageResponse data = new UserProfileResponse.MyPageResponse(
                user.getId(),
                user.getLoginId(),
                user.getEmail(),
                user.getNickname(),
                user.getGender()
        );
        return ResponseDto.setSuccess("SUCCESS", data);
    }
}

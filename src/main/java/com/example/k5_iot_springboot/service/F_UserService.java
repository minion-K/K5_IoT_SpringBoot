package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.F_User.request.UserProfileUpdateRequest;
import com.example.k5_iot_springboot.dto.F_User.response.UserProfileResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import com.example.k5_iot_springboot.security.UserPrincipal;
import jakarta.validation.Valid;

import java.nio.file.AccessDeniedException;

public interface F_UserService {
    ResponseDto<UserProfileResponse.MyPageResponse> getMyInfo(UserPrincipal principal);

    ResponseDto<UserProfileResponse.MyPageResponse> updateMyInfo(UserPrincipal principal, @Valid UserProfileUpdateRequest request);
}

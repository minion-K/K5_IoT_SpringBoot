package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.F_Auth.request.SignInRequest;
import com.example.k5_iot_springboot.dto.F_Auth.request.SignUpRequest;
import com.example.k5_iot_springboot.dto.F_Auth.response.SignInResponse;
import com.example.k5_iot_springboot.dto.ResponseDto;
import jakarta.validation.Valid;

public interface F_AuthService {
    void signUp(@Valid SignUpRequest req);

    ResponseDto<SignInResponse> signIn(@Valid SignInRequest req);
}

package com.example.k5_iot_springboot.dto.F_Auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public record PasswordChangeRequest(
        @NotBlank @Size(min = 8, max = 100)
        String newPassword
) {
}

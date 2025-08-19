package com.example.k5_iot_springboot.dto.D_Comment.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CommentUpdateRequestDto(
        @NotBlank(message = "내용은 필수 입력 값입니다.")
        @Size(max = 1000, message = "내용은 최대 1000자까지 입력할 수 있습니다.")
        String content
) {}

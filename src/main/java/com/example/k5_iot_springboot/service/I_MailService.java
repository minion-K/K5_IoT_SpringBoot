package com.example.k5_iot_springboot.service;

import com.example.k5_iot_springboot.dto.I_Mail.MailRequest;
import jakarta.validation.Valid;

public interface I_MailService {
    void sendEmail(MailRequest.@Valid SendMail req);

    void verifyEmail(String token);
}

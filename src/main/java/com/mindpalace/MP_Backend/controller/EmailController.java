package com.mindpalace.MP_Backend.controller;

import com.mindpalace.MP_Backend.model.dto.EmailAuthRequestDTO;
import com.mindpalace.MP_Backend.service.EmailService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"이메일 검증 컨트롤러"})
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/member/mailVerify")
    public String mailConfirm(@RequestBody EmailAuthRequestDTO emailDTO) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailDTO.getEmail());
        return authCode;
    }
}

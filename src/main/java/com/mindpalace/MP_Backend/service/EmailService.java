package com.mindpalace.MP_Backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    //의존성 주입 객체
    private final JavaMailSender emailSender;
    //타임리프 사용하기 위한 객체를 의존성 주입으로 가져옴
    private final SpringTemplateEngine templateEngine;
    private String authNum; //랜덤 인증 코드

    //랜덤 인증 코드 생성
    public void createCode(){
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0; i<8;i++){
            int index = random.nextInt(3);
            switch(index){
                case 0:
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode();//인증코드 생성
        String setFrom = "reheat1540@naver.com";
        String toEmail = email; // 받는 사람
        String title = "Mind Palace 회원가입 인증 번호";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
        message.setSubject(title); // 제목 설정
        message.setFrom(setFrom); // 받는 사람
        message.setText(setContext(authNum), "utf-8", "html");
        return message;
    }

    //타임리프 이용한 Context 설정
    public String setContext(String code){
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("/member/mail", context); // mail.html
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        //메일 전송 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);

        return authNum;// 인증코드 반환
    }
}
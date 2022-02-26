package com.lms.lms.component;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailComponent {

    private final JavaMailSender javaMailSender;

    public  void sendMailTest(){

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("yysi8771@naver.com");
        msg.setSubject("메일테스트");
        msg.setText("내용");

        javaMailSender.send(msg);


    }



}

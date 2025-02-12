package com.side.daangn.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    public void sendMailReject(String email, String code){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("이메일 인증 - Daangn");
            simpleMailMessage.setText("인증번호 : " + code);
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            System.out.println("Exception - MailUtil : sendMailReject");
            throw new RuntimeException(e);
        }

    }
}

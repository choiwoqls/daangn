package com.side.daangn.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    @Transactional
    public void sendMailReject(String email,String title, String text){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            System.out.println("Exception - MailUtil : sendMailReject");
            throw new RuntimeException(e);
        }

    }
}

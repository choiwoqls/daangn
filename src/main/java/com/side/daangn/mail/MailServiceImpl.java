package com.side.daangn.mail;

import com.side.daangn.dto.response.user.TimeLimitDTO;
import com.side.daangn.exception.DuplicateException;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.CodeUtil;
import com.side.daangn.util.MailUtil;
import com.side.daangn.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;

    private final RedisUtil redisUtil;

    private final MailUtil mailUtil;

    private final UserService userService;

    @Override
    @Async
    public Long sendMailAuth(String email) {
        try{

            long min = 3L;
            String code = CodeUtil.generate();
            String title = "이메일 인증 - Daangn";
            String text = "인증번호 : " + code;
//            mailUtil.sendMailReject(email,title,text);
            redisUtil.saveToken(email, code, min);

            return redisUtil.getRemainingValidTime(email);
        }catch (DuplicateException e){
            throw new DuplicateException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void sendMailPassword(String email, String password) {
        try{
            String title = "임시 비밀번호 - Daangn";
            String text = "임시 비밀번호 = " + password;
            mailUtil.sendMailReject(email,title,text);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

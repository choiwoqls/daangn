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

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;

    private final RedisUtil redisUtil;

    private final MailUtil mailUtil;

    private final UserService userService;

    @Override
    @Async
    public TimeLimitDTO sendMailAuth(String email) {
        try{
            if(userService.existsByEmail(email)){
                throw new DuplicateException("이미 사용중인 이메일");
            }
            TimeLimitDTO limit = new TimeLimitDTO();
            if(redisUtil.getToken(email) != null){
                Long remainingValidTime = redisUtil.getRemainingValidTime(email);
                limit.setMsg(remainingValidTime + "초 뒤에 다시 시도해 주세요.");
                limit.setTtl(remainingValidTime);
                return limit;
            }
            long min = 3L;
            String code = CodeUtil.generate();
            //mailUtil.sendMailReject(email,code);
            redisUtil.saveToken(email, code, min);
            long ttl = redisUtil.getRemainingValidTime(email);
            limit.setMsg("인증번호 전송.");
            limit.setTtl(ttl);
            return limit;
        }catch (DuplicateException e){
            throw new DuplicateException(e.getMessage());
        }catch (Exception e){
            System.out.println("here?");
            throw new RuntimeException(e);
        }
    }
}

package com.side.daangn.mail;

import com.side.daangn.dto.response.user.TimeLimitDTO;

public interface MailService {

    Long sendMailAuth(String email);
    void sendMailPassword(String email, String text);
}

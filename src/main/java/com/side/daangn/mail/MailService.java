package com.side.daangn.mail;

import com.side.daangn.dto.response.user.TimeLimitDTO;

public interface MailService {

    TimeLimitDTO sendMailAuth(String email);
}

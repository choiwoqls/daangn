package com.side.daangn.service.service.user;

import com.side.daangn.dto.request.auth.LoginDTO;
import com.side.daangn.dto.response.user.TimeLimitDTO;
import com.side.daangn.util.JWTAuthenticationResponse;

public interface AuthService {

    public JWTAuthenticationResponse login(LoginDTO loginDTO);

    public String checkCode(String email, String code);

    TimeLimitDTO sendMailAuth(String email);



}

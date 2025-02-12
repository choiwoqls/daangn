package com.side.daangn.service.service.user;

import com.side.daangn.dto.request.LoginDTO;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.util.JWTAuthenticationResponse;

import java.util.List;

public interface AuthService {

    public JWTAuthenticationResponse login(LoginDTO loginDTO);

    public String checkCode(String email, String code);



}

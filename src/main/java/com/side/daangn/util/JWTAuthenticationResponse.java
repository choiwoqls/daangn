package com.side.daangn.util;

import com.side.daangn.entitiy.user.User;
import lombok.Data;

@Data
public class JWTAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}


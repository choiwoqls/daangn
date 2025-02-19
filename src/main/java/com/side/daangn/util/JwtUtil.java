package com.side.daangn.util;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtUtil {
    public static SecretKey getKeyFromSecret(String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        //HmacSHA512
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
            //throw new UnauthorizedException("Unconfirmed Header");
        }
    }
}

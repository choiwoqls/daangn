package com.side.daangn.security;

import com.side.daangn.exception.UnauthorizedException;
import com.side.daangn.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiresIn}")
    private long EXPIRATION_TIME;

     public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        // Extract roles from userPrincipal and convert them to a list of role names
          try {
              String jwt =  Jwts.builder()
                      .id(userPrincipal.getId() + "")
                      .subject(userPrincipal.getId() + "")
                      .claim("user", userPrincipal)
                      .issuedAt(new Date(System.currentTimeMillis()))
                      .expiration(expiryDate)
                      .signWith(JwtUtil.getKeyFromSecret(SECRET_KEY))
                      .compact();
              return jwt;
          }catch (Exception e){
              e.printStackTrace();
              throw new RuntimeException(e);

          }
    }

    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                     .verifyWith(JwtUtil.getKeyFromSecret(SECRET_KEY))
                     .build()
                     .parseSignedClaims(token).getPayload();
            return claims.getSubject();
        }catch (Exception e){
            System.out.println(e.getClass());
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(JwtUtil.getKeyFromSecret(SECRET_KEY))
                    .build()
                    .parseSignedClaims(authToken).getPayload();

        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token" + ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token" + ex);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty" + ex);
        }
        return true;
    }


}

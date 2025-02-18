package com.side.daangn.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.security.JwtTokenProvider;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.ApiResponse;
import com.side.daangn.util.JWTAuthenticationResponse;
import com.side.daangn.util.RedisUtil;
import com.side.daangn.util.UserUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Log4j2
@Component
public class SocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("1 : " + authentication.getPrincipal().toString());
        //jwt 생성, redis 저장.
        log.info("ID : " + UserUtils.getLoggedInUser().getId());
        UserPrincipal user = UserUtils.getLoggedInUser();

        UUID user_id = user.getId();
        String jwt = tokenProvider.generateToken(user_id);
        redisUtil.saveToken(String.valueOf(user_id), jwt, 60);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + jwt + "\"}");
    }
}

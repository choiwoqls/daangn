package com.side.daangn.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class SocialLoginFailedHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 에러 코드 설정
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request

        // 응답 타입을 JSON으로 설정
        response.setContentType("application/json");

        // 에러 메시지 작성
        String errorMessage = "OAuth2 로그인 실패: " + exception.getMessage();
        String errorCode = "OAUTH2_ERROR";  // 사용자 정의 에러 코드

        // JSON 형식으로 클라이언트에 에러 메시지와 코드 전송
        String jsonResponse = String.format("{\"error\": \"%s\", \"message\": \"%s\"}", errorCode, errorMessage);

        // 응답에 에러 메시지 보내기
        response.getWriter().write(jsonResponse);
    }
}

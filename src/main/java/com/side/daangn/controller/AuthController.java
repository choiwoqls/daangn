package com.side.daangn.controller;


import com.side.daangn.dto.request.auth.CodeVerifyDTO;
import com.side.daangn.dto.request.auth.LoginDTO;
import com.side.daangn.dto.request.auth.SignUpDTO;
import com.side.daangn.dto.response.user.TimeLimitDTO;
import com.side.daangn.mail.MailService;
import com.side.daangn.service.service.user.AuthService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.ApiResponse;
import com.side.daangn.util.JWTAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final MailService mailService;

    @PostMapping("/login")
    @Operation(summary = "유저 로그인", description = "유저 로그인 API")
    public ResponseEntity<ApiResponse<JWTAuthenticationResponse>> login(@RequestBody @Valid LoginDTO LoginDTO) {
        return ApiResponse.success(authService.login(LoginDTO)).toResponseEntity();
    }


    @PostMapping("/sign-up")
    @Operation(summary = "유저 회원가입", description = "유저 회원가입 API")
    public ResponseEntity<ApiResponse<String>> signUp(
            @RequestPart("sign-up") @Valid SignUpDTO signUpDto,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) {
        return ApiResponse.success(userService.signUp(signUpDto, file)).toResponseEntity();
    }

    @GetMapping("/verify-email")
    @Operation(summary = "이메일 인증 코드 전송", description = "유저 회원가입 전 이메일 인증 코드 전송 API")
    @ResponseBody
    public ResponseEntity<ApiResponse<TimeLimitDTO>> verifyEmail(@RequestParam @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식으로 입력해 주세요.") String email){
        TimeLimitDTO limit = authService.sendMailAuth(email);
        return ApiResponse.success(limit).toResponseEntity();
    }

    @GetMapping("/verify-code")
    @Operation(summary = "인증 코드 확인", description = "이메일 인증 코드 확인 API")
    public ResponseEntity<ApiResponse<String>> verifyCode(@ModelAttribute @Valid CodeVerifyDTO dto){
        return ApiResponse.success(authService.checkCode(dto.getEmail(), dto.getCode())).toResponseEntity();
    }






}

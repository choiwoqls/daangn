package com.side.daangn.controller;


import com.side.daangn.dto.request.CodeVerifyDTO;
import com.side.daangn.dto.request.LoginDTO;
import com.side.daangn.dto.request.SignUpDTO;
import com.side.daangn.dto.response.user.TimeLimitDTO;
import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.mail.MailService;
import com.side.daangn.service.service.user.AuthService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.ApiResponse;
import com.side.daangn.util.JWTAuthenticationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<ApiResponse<JWTAuthenticationResponse>> login(@RequestBody @Valid LoginDTO LoginDTO) {
        return ApiResponse.success(authService.login(LoginDTO)).toResponseEntity();
    }


    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody @Valid SignUpDTO signUpDto) {
        return ApiResponse.success(userService.signUp(signUpDto)).toResponseEntity();
    }

    @GetMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<ApiResponse<TimeLimitDTO>> verifyEmail(@RequestParam @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "올바른 이메일 형식으로 입력해 주세요.") String email){
        TimeLimitDTO limit = mailService.sendMailAuth(email);
        return ApiResponse.success(limit).toResponseEntity();
    }

    @GetMapping("/verify-code")
    public ResponseEntity<ApiResponse<String>> verifyCode(@ModelAttribute @Valid CodeVerifyDTO dto){
        return ApiResponse.success(authService.checkCode(dto.getEmail(), dto.getCode())).toResponseEntity();
    }




}

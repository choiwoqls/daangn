package com.side.daangn.controller;


import com.side.daangn.dto.request.LoginDTO;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.ApiResponse;
import com.side.daangn.util.JWTAuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> login(HttpServletRequest request) {
        return ApiResponse.success(userService.logout(request)).toResponseEntity();
    }


}

package com.side.daangn.controller;


import com.side.daangn.dto.response.product.ProductResponseDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.service.service.product.ProductService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ProductService productService;


    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> login() {
        return ApiResponse.success(userService.logout()).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> userDetail(@PathVariable UUID id) {
        return ApiResponse.success(userService.findById(id)).toResponseEntity();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<SearchPageDTO>>userProductList(@PathVariable UUID id, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize){
        return ApiResponse.success(productService.userProductList(id, pageNum, pageSize)).toResponseEntity();
    }

}

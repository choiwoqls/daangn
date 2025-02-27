package com.side.daangn.controller;

import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.service.service.product.CategoryService;
import com.side.daangn.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService  categoryService;


    @GetMapping("/categories")
    @Operation(summary = "카테고리 목록", description = "카테고리 목록 API")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>>categoryList(@RequestParam(required = false, defaultValue = "0") String type){
        return ApiResponse.success(categoryService.findByType(type)).toResponseEntity();
    }


    @PostMapping("/category")
    @Operation(summary = "카테고리 추가", description = "카테고리 추가 API")
    public ResponseEntity<ApiResponse<String>> addCategory(@RequestBody @Valid CategoryDTO categoryDTO){
        return ApiResponse.success(categoryService.addCategory(categoryDTO)).toResponseEntity();
    }

}

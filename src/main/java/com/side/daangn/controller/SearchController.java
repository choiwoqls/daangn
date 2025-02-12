package com.side.daangn.controller;

import com.side.daangn.dto.response.product.SearchDTO;
import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.service.service.product.SearchService;
import com.side.daangn.util.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping("/hottest")
    public ResponseEntity<ApiResponse<List<SearchDTO>>> categoryList(@RequestParam(required = false, defaultValue = "0") String type){
        return ApiResponse.success(searchService.hottest(type)).toResponseEntity();
    }

}

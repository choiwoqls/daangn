package com.side.daangn.controller;

import com.side.daangn.dto.request.ProductDTO;
import com.side.daangn.dto.request.SearchOptionDTO;
import com.side.daangn.dto.response.product.ProductDetailDTO;
import com.side.daangn.dto.response.product.ProductResponseDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.entitiy.product.Product;
import com.side.daangn.service.service.product.ProductService;
import com.side.daangn.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;


    @GetMapping
    public ResponseEntity<ApiResponse<SearchPageDTO>> products_search(@ModelAttribute @Valid SearchOptionDTO dto){
        return ApiResponse.success(productService.products_search(dto)).toResponseEntity();
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ApiResponse<ProductDetailDTO>> products_detail(@PathVariable UUID product_id){
        System.out.println("product_id :" + product_id);
        return ApiResponse.success(productService.productDetail(product_id)).toResponseEntity();
    }

//    @GetMapping("/{user_id}/")
//    public ResponseEntity<ApiResponse<ProductDetailDTO>> userProductList(@PathVariable UUID product_id){
//        System.out.println("product_id :" + product_id);
//        return ApiResponse.success(productService.productDetail(product_id)).toResponseEntity();
//    }


    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> addProduct(@RequestBody @Valid ProductDTO product){
        return ApiResponse.success(productService.addProduct(product)).toResponseEntity();
    }



}

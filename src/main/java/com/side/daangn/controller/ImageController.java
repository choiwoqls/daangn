package com.side.daangn.controller;

import com.side.daangn.S3.S3Service;
import com.side.daangn.service.service.product.ProductService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.service.service.util.ImageService;
import com.side.daangn.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private final S3Service s3Service;

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ImageService imageService;


    @GetMapping("/product/{filename}")
    @Operation(summary = "상품 이미지", description = "상품 이미지 API")
    public ResponseEntity<byte[]> getProductImg(@PathVariable("filename")String fileName){
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(s3Service.getImage(fileName));
    }

    @PostMapping("/product")
    @Operation(summary = "상품 이미지 업로드", description = "다중 상품 이미지 업로드 API")
    public ResponseEntity<ApiResponse<List<String>>> productImgUpload(
            @RequestPart(value = "file", required = false) List<MultipartFile> files
            ){
        return ApiResponse.success(imageService.uploadImageList(files)).toResponseEntity();
    }

    @GetMapping("/user/{filename}")
    @Operation(summary = "유저 이미지", description = "유저 이미지 API")
    public ResponseEntity<byte[]> getUserImg(@PathVariable("filename")String fileName){
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(s3Service.getImage(fileName));
    }

    @PostMapping("/user")
    @Operation(summary = "유저 이미지 업로드", description = "유저 이미지 업로드 API")
    public ResponseEntity<ApiResponse<String>> userImgUpload(
            @RequestPart(value = "file", required = false) MultipartFile file
            ){
        return ApiResponse.success(imageService.uploadImage(file)).toResponseEntity();
    }

    @PostMapping("/community")
    @Operation(summary = "동네생활 이미지 업로드", description = "동네생활 다중 이미지 업로드 API")
    public ResponseEntity<ApiResponse<List<String>>> communityImgUpload(
            @RequestPart(value = "file", required = false) List<MultipartFile> files
            ){
        return ApiResponse.success(imageService.uploadImageList(files)).toResponseEntity();
    }

    @GetMapping("/community/{filename}")
    @Operation(summary = "동네생활 이미지", description = "동네생활 이미지 API")
    public ResponseEntity<byte[]> getCommunityImg(@PathVariable("filename")String fileName){
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(s3Service.getImage(fileName));
    }



}

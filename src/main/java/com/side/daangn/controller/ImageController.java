package com.side.daangn.controller;

import com.side.daangn.S3.S3Service;
import com.side.daangn.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private final S3Service s3Service;


    @GetMapping("/product/{filename}")
    public ResponseEntity<byte[]> getProductImg(@PathVariable("filename")String fileName){
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(s3Service.getImage(fileName));
    }

    @GetMapping("/user/{filename}")
    public ResponseEntity<byte[]> getUserImg(@PathVariable("filename")String fileName){
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(s3Service.getImage(fileName));
    }




}

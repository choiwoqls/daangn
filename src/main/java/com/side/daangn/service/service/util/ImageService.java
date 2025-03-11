package com.side.daangn.service.service.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<String> uploadImageList(List<MultipartFile> files);
    String uploadImage(MultipartFile file);

}

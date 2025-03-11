package com.side.daangn.service.serviceImpl.util;

import com.side.daangn.S3.S3Service;
import com.side.daangn.service.service.util.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Autowired
    private final S3Service s3Service;

    @Override
    public List<String> uploadImageList(List<MultipartFile> files) {
        try {
            if(files.isEmpty()){
                throw new IllegalArgumentException("1개 이상의 사진을 등록해 주세요.");
            }
            List<String> imgList = new ArrayList<>();
            for(MultipartFile file : files){
                String fileName = s3Service.uploadImage(file, UUID.randomUUID());
                imgList.add(fileName);
            }
            return imgList;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("다중 파일 업로드");
        }
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            return s3Service.uploadImage(file, UUID.randomUUID());
        }catch (Exception e){
            throw new RuntimeException("단일 파일 업로드");
        }
    }
}

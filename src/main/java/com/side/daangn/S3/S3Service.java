package com.side.daangn.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void imageCheck(MultipartFile image){
        try {
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("파일 크기는 5MB 이하로 업로드해야 합니다.");
            }
            String contentType = image.getContentType();
            if (contentType == null ||
                (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                throw new IllegalArgumentException("지원되지 않는 파일 형식입니다. (JPEG, PNG만 허용)");
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Transactional
    public String uploadImage(MultipartFile image, UUID id) throws IOException {
        try{
            this.imageCheck(image);
            String contentType = image.getContentType().split("/")[1];

            String fileName = id +"."+contentType ; // 고유한 파일 이름 생성


            // 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            metadata.setContentLength(image.getSize());

            // S3에 파일 업로드 요청 생성
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);

            // S3에 파일 업로드
            amazonS3.putObject(putObjectRequest);

            return fileName;

        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public byte[] getImage(String filename){
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, filename));
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//
//    private String getPublicUrl(String fileName) {
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
//    }


}

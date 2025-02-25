package com.side.daangn.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
//            this.imageCheck(image);
//            String contentType = image.getContentType().split("/")[1];
//
//            String fileName = id +"."+contentType ; // 고유한 파일 이름 생성
//
//
//            // 메타데이터 설정
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(image.getContentType());
//            metadata.setContentLength(image.getSize());
//
//            // S3에 파일 업로드 요청 생성
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);
//
//            // S3에 파일 업로드
//            amazonS3.putObject(putObjectRequest);
//
//            return fileName;
            //MultipartFile을 File로 변환
            File convertedFile = convertMultipartFileToFile(image, id);

            // WebP 형식으로 변환
            File webpFile = convertToWebp(convertedFile);

            // AWS S3에 업로드
            String s3Key = uploadFileToS3(webpFile);

            // 변환된 WebP 파일 삭제 (임시 파일 정리)
            webpFile.delete();

            return s3Key;

        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile, UUID id) throws IOException {
        String fileName = id.toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir"), fileName);
        multipartFile.transferTo(file);
        return file;
    }

    private File convertToWebp(File inputFile) throws IOException {
        // WebP로 변환할 임시 파일
        File webpFile = new File(inputFile.getParent(), UUID.randomUUID().toString() + ".webp");

        // 이미지를 WebP 형식으로 변환 (예시로 ImmutableImage를 사용)
        ImmutableImage.loader().fromFile(inputFile)
            .output(WebpWriter.DEFAULT, webpFile);

        return webpFile;
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

    private String uploadFileToS3(File file) throws IOException {
        String fileName = file.getName();
        InputStream inputStream = new FileInputStream(file);

        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());

        // S3 버킷에 업로드
        amazonS3.putObject(bucket, fileName, inputStream, metadata);

        // 업로드 후 S3의 객체 키 반환
        return fileName;
    }
//
//    private String getPublicUrl(String fileName) {
//        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
//    }


}

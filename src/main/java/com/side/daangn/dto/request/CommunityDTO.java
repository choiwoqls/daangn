package com.side.daangn.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.side.daangn.entitiy.community.Community;
import com.side.daangn.entitiy.community.Community_Image;
import com.side.daangn.service.service.util.ImageService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CommunityDTO {

    private UUID id;

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;

    @NotBlank(message = "설명은 필수 입니다.")
    private String body;

    private String img;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    @Min(value = 1, message = "카테고리를 선택해 주세요")
    private int category_id;

    private List<String> imgList;

    public CommunityDTO(Community community){
        this.id = community.getId();
        this.title = community.getTitle();
        this.body = community.getBody();
        this.img = community.getImage();
        this.createdAt = community.getCreatedAt();
        this.updatedAt = community.getUpdatedAt();
        this.category_id = community.getCategory().getId();
        this.imgList = community.getCommunityImages().stream().map(Community_Image::getFileName).toList();
    }

}

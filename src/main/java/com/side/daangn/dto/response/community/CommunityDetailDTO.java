package com.side.daangn.dto.response.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.side.daangn.dto.response.user.UserProfileDTO;
import com.side.daangn.entitiy.community.Community;
import com.side.daangn.entitiy.community.Community_Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CommunityDetailDTO {

    private UUID id;

    private String title;
    private String body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private String category_name;

    private List<String> imgList;

    private UserProfileDTO userProfileDTO;

    public CommunityDetailDTO(Community community){
        this.id = community.getId();
        this.title = community.getTitle();
        this.body = community.getBody();
        this.createdAt = community.getCreatedAt();
        this.updatedAt = community.getUpdatedAt();
        this.category_name = community.getCategory().getName();
        this.imgList = community.getCommunityImages().stream()
                .map(Community_Image::getFileName).toList();
        this.userProfileDTO = new UserProfileDTO(community.getUser());
    }
}

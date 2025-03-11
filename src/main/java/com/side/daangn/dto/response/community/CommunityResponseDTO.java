package com.side.daangn.dto.response.community;

import com.side.daangn.dto.response.user.UserProfileDTO;
import com.side.daangn.entitiy.community.Community;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResponseDTO {

    private UUID id;

    private String title;

    private String body;

    private String image;

    private String category_name;

    private LocalDateTime updatedAt;

    private int commentCount;

    public CommunityResponseDTO(Community community){
        this.id = community.getId();
        this.title = community.getTitle();
        this.body = community.getBody();
        this.image = community.getImage();
        this.category_name = community.getCategory().getName();
        this.updatedAt = community.getUpdatedAt();
        this.commentCount = community.getCommunityComments().size();
    }

}

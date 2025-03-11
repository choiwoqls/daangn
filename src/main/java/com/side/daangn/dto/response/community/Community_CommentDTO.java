package com.side.daangn.dto.response.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.side.daangn.dto.response.user.UserProfileDTO;
import com.side.daangn.entitiy.community.Community_Comment;
import com.side.daangn.entitiy.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Community_CommentDTO {

    private Long id;
    private UUID community_id;
    private UserProfileDTO user;
    private String body;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public Community_CommentDTO(Community_Comment comment){
        this.id = comment.getId();
        this.community_id = comment.getCommunity().getId();
        this.user = new UserProfileDTO(comment.getUser());
        this.body = comment.getBody();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}

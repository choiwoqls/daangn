package com.side.daangn.service.service.community;

import com.side.daangn.dto.response.community.Community_CommentDTO;

import java.util.List;
import java.util.UUID;

public interface Community_CommentService {
    List<Community_CommentDTO> communityComments(UUID community_id);
    Community_CommentDTO addCommunityComment(UUID community_id, String body);
}

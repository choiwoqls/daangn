package com.side.daangn.repository.community;

import com.side.daangn.entitiy.community.Community_Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface Community_CommentRepository extends JpaRepository<Community_Comment, Long> {

    List<Community_Comment> findByCommunityId(UUID community_id);


}

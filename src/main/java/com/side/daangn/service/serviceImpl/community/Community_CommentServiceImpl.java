package com.side.daangn.service.serviceImpl.community;

import com.side.daangn.dto.response.community.Community_CommentDTO;
import com.side.daangn.entitiy.community.Community;
import com.side.daangn.entitiy.community.Community_Comment;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.exception.NotFoundException;
import com.side.daangn.repository.community.Community_CommentRepository;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.service.service.community.CommunityService;
import com.side.daangn.service.service.community.Community_CommentService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Community_CommentServiceImpl implements Community_CommentService {

    @Autowired
    private final Community_CommentRepository communityCommentRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final CommunityService communityService;

    @Override
    public List<Community_CommentDTO> communityComments(UUID community_id) {
        try{
            if(!communityService.communityCheck(community_id)){
                throw new NotFoundException("찾을 수 없는 게시글");
            }
            return communityCommentRepository.findByCommunityId(community_id).stream()
                    .map(Community_CommentDTO::new).toList();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Community_CommentDTO addCommunityComment(UUID community_id, String body) {
        try{
            if(!communityService.communityCheck(community_id)){
                throw new NotFoundException("찾을 수 없는 게시글");
            }

            Community_Comment comment = new Community_Comment();

            UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
            User user = new User();
            user.setId(userService.findById(userPrincipal.getId()).getId());
            comment.setUser(user);

            Community community = new Community();
            community.setId(community_id);
            comment.setCommunity(community);

            comment.setBody(body);

            comment = communityCommentRepository.save(comment);

            return new Community_CommentDTO(comment);

        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

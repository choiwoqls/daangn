package com.side.daangn.controller;

import com.side.daangn.dto.request.CommunityDTO;
import com.side.daangn.dto.request.CommunitySearchDTO;
import com.side.daangn.dto.response.community.CommunityDetailDTO;
import com.side.daangn.dto.response.community.Community_CommentDTO;
import com.side.daangn.dto.response.user.ContentPageDTO;
import com.side.daangn.service.service.community.CommunityService;
import com.side.daangn.service.service.community.Community_CommentService;
import com.side.daangn.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/community")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@RequiredArgsConstructor
public class CommunityController {

    @Autowired
    private final CommunityService communityService;

    @Autowired
    private final Community_CommentService communityCommentService;

    @GetMapping()
    @Operation(summary = "동네생활 게시글 리스트", description = "동네생활 게시글 리스트 API")
    public ResponseEntity<ApiResponse<ContentPageDTO>> communityList(@ModelAttribute @Valid CommunitySearchDTO dto){
        return ApiResponse.success(communityService.communityList(dto)).toResponseEntity();
    }

    @PostMapping()
    @Operation(summary = "동네생활 게시글 추가", description = "동네생활 게시글 추가 API")
    public ResponseEntity<ApiResponse<CommunityDTO>> addCommunity(@RequestBody @Valid CommunityDTO dto){
        return ApiResponse.success(communityService.addCommunity(dto)).toResponseEntity();
    }

    @GetMapping("/{community_id}")
    @Operation(summary = "동네생활 게시글 상세", description = "동네생활 게시글 상세 API")
    public ResponseEntity<ApiResponse<CommunityDetailDTO>> communityDetail(@PathVariable UUID community_id){
        return ApiResponse.success(communityService.communityDetail(community_id)).toResponseEntity();
    }

    @GetMapping("/{community_id}/comment")
    @Operation(summary = "동네생활 게시글 댓글", description = "동네생활 게시글 댓글 API")
    public ResponseEntity<ApiResponse<List<Community_CommentDTO>>> communityComments(@PathVariable UUID community_id){
        return ApiResponse.success(communityCommentService.communityComments(community_id)).toResponseEntity();
    }

    @PostMapping("/{community_id}/comment")
    @Operation(summary = "동네생활 게시글 댓글 추가", description = "동네생활 게시글 댓글 추가 API")
    public ResponseEntity<ApiResponse<Community_CommentDTO>> addCommunityComment(@PathVariable UUID community_id, @RequestBody @NotBlank(message = "댓글은 공백 일 수 없습니다.")String body){
        return ApiResponse.success(communityCommentService.addCommunityComment(community_id, body)).toResponseEntity();
    }

}

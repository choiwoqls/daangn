package com.side.daangn.service.service.community;

import com.side.daangn.dto.request.CommunityDTO;
import com.side.daangn.dto.request.CommunitySearchDTO;
import com.side.daangn.dto.response.community.CommunityDetailDTO;
import com.side.daangn.dto.response.user.ContentPageDTO;
import com.side.daangn.entitiy.community.Community_Comment;

import java.util.List;
import java.util.UUID;

public interface CommunityService {
    ContentPageDTO communityList(CommunitySearchDTO dto);
    CommunityDTO addCommunity(CommunityDTO dto);
    CommunityDetailDTO communityDetail(UUID community_id);
    boolean communityCheck(UUID community_id);

}

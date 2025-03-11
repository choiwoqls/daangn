package com.side.daangn.service.serviceImpl.community;

import com.side.daangn.dto.request.CommunityDTO;
import com.side.daangn.dto.request.CommunitySearchDTO;
import com.side.daangn.dto.response.community.CommunityDetailDTO;
import com.side.daangn.dto.response.community.CommunityResponseDTO;
import com.side.daangn.dto.response.user.ContentPageDTO;
import com.side.daangn.entitiy.community.Community;
import com.side.daangn.entitiy.community.Community_Image;
import com.side.daangn.entitiy.product.Category;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.exception.NotFoundException;
import com.side.daangn.repository.community.CommunityRepository;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.service.service.community.CommunityService;
import com.side.daangn.service.service.community.Community_ImageService;
import com.side.daangn.service.service.product.CategoryService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private final CommunityRepository communityRepository;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final Community_ImageService communityImageService;


    @Override
    public ContentPageDTO communityList(CommunitySearchDTO dto) {

        try {
            int page = dto.getPageNum() == null || dto.getPageNum()<=0 ? 0 : dto.getPageNum()-1;
            int size = dto.getPageSize() == null || dto.getPageSize()<=0 ? 10 : dto.getPageSize();
            Pageable pageable = PageRequest.of(page, size);

            Integer category_id = null;
            if(dto.getCategory_id() != null && !dto.getCategory_id().isEmpty()){
                String cate_str = dto.getCategory_id();
                category_id = cate_str.matches("\\d+") ? Integer.valueOf(cate_str) : null;
            }
            Page<Community> communityPage = communityRepository.communityList(pageable, category_id);

            List<CommunityResponseDTO> communities = communityPage.getContent().stream()
                    .map(CommunityResponseDTO::new).toList();

            int maxPage = communityPage.getTotalPages();
            return new ContentPageDTO(page,size,maxPage,communities);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public CommunityDTO addCommunity(CommunityDTO dto) {
        try{
            if(!categoryService.existsById(dto.getCategory_id())){
                throw new NotFoundException("잘못된 카테고리 선택");
            }
            Community community = new Community();
            community.setTitle(dto.getTitle());
            community.setBody(dto.getBody());
            community.setImage(dto.getImgList().get(0));

            UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
            User user = new User();
            user.setId(userService.findById(userPrincipal.getId()).getId());
            community.setUser(user);

            Category category = new Category();
            category.setId(dto.getCategory_id());
            community.setCategory(category);

            community = communityRepository.save(community);

            for(String imgName : dto.getImgList()){
                Community_Image communityImage = new Community_Image();
                communityImage.setCommunity(community);
                communityImage.setFileName(imgName);
                communityImageService.save(communityImage);
            }
            return new CommunityDTO(community);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public CommunityDetailDTO communityDetail(UUID community_id) {
        try{
            Community community = communityRepository.findById(community_id).orElseThrow(()->new NotFoundException("찾을 수 없는 게시글"));
            return new CommunityDetailDTO(community);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean communityCheck(UUID community_id) {
        try{
            return communityRepository.existsById(community_id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}

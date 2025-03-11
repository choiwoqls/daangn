package com.side.daangn.repository.community;

import com.side.daangn.dto.response.community.CommunityResponseDTO;
import com.side.daangn.entitiy.community.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID> {


    @Query("SELECT c " +
            "FROM Community c " +
            "WHERE (:category_id IS NULL OR c.category.id = :category_id) " +
            "ORDER BY c.updatedAt desc")
    Page<Community> communityList(Pageable pageable, Integer category_id);

}

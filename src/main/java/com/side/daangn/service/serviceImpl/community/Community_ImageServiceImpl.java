package com.side.daangn.service.serviceImpl.community;

import com.side.daangn.entitiy.community.Community_Image;
import com.side.daangn.repository.community.Community_ImageRepository;
import com.side.daangn.service.service.community.Community_ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Community_ImageServiceImpl implements Community_ImageService {

    @Autowired
    private final Community_ImageRepository communityImageRepository;


    @Override
    @Transactional
    public Community_Image save(Community_Image communityImage) {
        try{
            return communityImageRepository.save(communityImage);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

package com.side.daangn.service.serviceImpl.product;


import com.side.daangn.dto.response.product.SearchDTO;
import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.entitiy.product.Search;
import com.side.daangn.repository.product.SearchRepository;
import com.side.daangn.service.service.product.SearchService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    @Autowired
    private final SearchRepository searchRepository;


    @Override
    public Search findBySearch(String search) {
        try{
            return searchRepository.findBySearch(search).orElse(null);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void searchPlus(String search) {
        try{
            searchRepository.searchPlus(search);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Search save(Search search) {
        try{
            return searchRepository.save(search);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SearchDTO> hottest(String type) {
        try{
            if(!type.matches("\\d+")){
                //throw new BadRequestException("잘못된 type 입력");
                return List.of();
            }
            return searchRepository.findTop5ByTypeOrderByCountDesc(Integer.parseInt(type)).stream()
                    .map(SearchDTO::new).toList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

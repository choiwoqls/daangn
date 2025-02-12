package com.side.daangn.service.serviceImpl.product;


import com.side.daangn.dto.response.product.SearchDTO;
import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.entitiy.product.Search;
import com.side.daangn.repository.product.SearchRepository;
import com.side.daangn.service.service.product.SearchService;
import com.side.daangn.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    @Autowired
    private final SearchRepository searchRepository;

    @Autowired
    private final RedisUtil redisUtil;

    @Override
    public Search findBySearch(String search) {
        try{
            return searchRepository.findBySearch(search).orElse(null);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void searchPlus(String search, long count) {
        try{
            searchRepository.searchPlus(search, count);
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

    //스케줄러 실행 함수
    @Override
    public void saveSearchToDB() {
       try{
           Set<String> keys = redisUtil.getKeysByPattern("search_*");
		if (keys != null && !keys.isEmpty()){
			for(String key : keys){
				String search = key.split("_")[1];
				String type = key.split("_")[2];
				long count = Long.parseLong(redisUtil.getToken(key));
				Search searchEntity = searchRepository.findBySearch(search).orElse(null);
				if(searchEntity!= null){
					this.searchPlus(search, count);
				}else{
					searchEntity = new Search();
					searchEntity.setSearch(search);
					searchEntity.setType(Integer.parseInt(type));
					searchEntity.setCount(count);
					searchRepository.save(searchEntity);
				}
			}
		}
		redisUtil.deleteKeys(keys);
       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
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

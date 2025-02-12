package com.side.daangn.service.service.product;

import com.side.daangn.dto.response.product.SearchDTO;
import com.side.daangn.entitiy.product.Search;

import java.util.List;
import java.util.Optional;

public interface SearchService {

    Search findBySearch(String search);

    void searchPlus(String search, long count);

    Search save(Search search);

    void saveSearchToDB();

    List<SearchDTO> hottest(String type);

}

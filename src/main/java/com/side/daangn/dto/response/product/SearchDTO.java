package com.side.daangn.dto.response.product;

import com.side.daangn.entitiy.product.Search;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private Long id;
    private String search;
    private int type;

    public SearchDTO(Search search){
        this.id = search.getId();
        this.search = search.getSearch();
        this.type = search.getType();
    }



}

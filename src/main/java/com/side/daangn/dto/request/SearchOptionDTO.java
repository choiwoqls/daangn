package com.side.daangn.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class SearchOptionDTO {

    private String search;

    private String category_id;

    private String only_on_sale;

    private String price;

    private String sort_by;

    private Integer page;

    private Integer pageSize;


}

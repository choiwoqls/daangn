package com.side.daangn.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommunitySearchDTO {

    private Integer pageNum;

    private Integer pageSize;

    private String category_id;

}

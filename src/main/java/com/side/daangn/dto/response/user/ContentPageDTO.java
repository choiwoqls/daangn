package com.side.daangn.dto.response.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ContentPageDTO {

    private int pageNum;
    private int pageSize;
    private int maxPage;
    private List<?> content;

    public ContentPageDTO(int pageNum, int pageSize, int maxPage, List<?> content){
        this.pageNum = pageNum + 1;
        this.pageSize = pageSize;
        this.maxPage = maxPage;
        this.content = content;
    }
}

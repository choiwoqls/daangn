package com.side.daangn.dto.response.user;

import com.side.daangn.dto.response.product.ProductResponseDTO;
import com.side.daangn.entitiy.product.Category;
import com.side.daangn.entitiy.product.Product;
import com.side.daangn.entitiy.product.Search;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchPageDTO {

    private int pageNum;
    private int pageSize;
    private int maxPage;
    private Long elementCount;
    private List<ProductResponseDTO> products;

    public SearchPageDTO(int pageNum, int pageSize,int maxPage, Long elementCount, List<ProductResponseDTO> products){
        this.pageNum = pageNum + 1;
        this.pageSize = pageSize;
        this.maxPage = maxPage;
        this.elementCount = elementCount;
        this.products = products;
    }
}

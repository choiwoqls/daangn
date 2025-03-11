package com.side.daangn.service.service.product;

import com.side.daangn.dto.request.ProductDTO;
import com.side.daangn.dto.request.SearchOptionDTO;
import com.side.daangn.dto.response.product.ProductDetailDTO;
import com.side.daangn.dto.response.user.ContentPageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ContentPageDTO products_search(SearchOptionDTO dto);
    ContentPageDTO userProductList(UUID id, Integer pageNum, Integer pageSize);
    ProductDTO addProduct(ProductDTO productDto);
    ProductDetailDTO productDetail(UUID id);
}

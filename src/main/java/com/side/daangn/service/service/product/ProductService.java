package com.side.daangn.service.service.product;

import com.side.daangn.dto.request.ProductDTO;
import com.side.daangn.dto.request.SearchOptionDTO;
import com.side.daangn.dto.response.product.ProductDetailDTO;
import com.side.daangn.dto.response.product.ProductResponseDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.entitiy.product.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    SearchPageDTO products_search(SearchOptionDTO dto);
    ProductDTO addProduct(ProductDTO productDto);
    ProductDetailDTO productDetail(UUID id);
}

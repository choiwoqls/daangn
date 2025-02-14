package com.side.daangn.service.service.product;

import com.side.daangn.dto.response.product.Product_ImageDTO;
import com.side.daangn.entitiy.product.Product_Image;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Product_ImageService {
    Product_Image save(Product_Image product_image);
    List<Product_ImageDTO> productImageList(UUID product_id);
}

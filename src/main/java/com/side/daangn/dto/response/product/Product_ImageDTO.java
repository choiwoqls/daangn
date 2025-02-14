package com.side.daangn.dto.response.product;

import com.side.daangn.entitiy.product.Product_Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Product_ImageDTO {
    private String fileName;

    public Product_ImageDTO(Product_Image product_image){
        this.fileName=product_image.getFileName();
    }
}

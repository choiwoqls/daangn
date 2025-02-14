package com.side.daangn.dto.response.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.side.daangn.dto.response.user.UserProfileDTO;
import com.side.daangn.entitiy.product.Product;
import com.side.daangn.entitiy.product.Product_Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductDetailDTO {

    private String title;
    private String body;
    private int price;
    private int only_on_sale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private int category_id;

    private UserProfileDTO user;

    private List<String> product_images;

    public ProductDetailDTO(Product product){
        this.title = product.getTitle();
        this.body = product.getBody();
        this.price = product.getPrice();
        this.only_on_sale = product.getOnly_on_sale();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.category_id = product.getCategory().getId();
        this.user = new UserProfileDTO(product.getUser());
        this.product_images =
                product.getProductImages().stream()
                        .map(Product_Image::getFileName).toList();

    }



}

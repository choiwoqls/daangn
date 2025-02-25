package com.side.daangn.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.side.daangn.entitiy.product.Category;
import com.side.daangn.entitiy.product.Product;
import com.side.daangn.entitiy.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductDTO {

    private UUID id;

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;

    @NotBlank(message = "설명은 필수 입니다.")
    private String body;

    @Min(value = 0, message = "가격은 0원 이상으로 입력해주세요.")
    private int price;

    @Min(value = 0, message = "잘못된 선택 : only_on_sale")
    @Max(value = 1, message = "잘못된 선택 : only_on_sale")
    private int only_on_sale;

    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    @Min(value = 1, message = "카테고리를 선택해 주세요")
    private int category_id;

    private List<String> imgList;

    public ProductDTO(Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.body = product.getBody();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.price = product.getPrice();
        this.category_id = product.getCategory().getId();
        this.only_on_sale = product.getOnly_on_sale();
        this.image=product.getImage();
    }

}

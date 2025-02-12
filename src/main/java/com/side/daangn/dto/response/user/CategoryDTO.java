package com.side.daangn.dto.response.user;

import com.side.daangn.entitiy.product.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

    private int id;

    @NotBlank(message = "카테고리 이름을 입력해주세요")
    private String name;

    @Min(value = 0, message = "잘못된 타입 번호")
    @Max(value = 3, message = "잘못된 타입 번호")
    private int type;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name=category.getName();
        this.type=category.getType();
    }


}

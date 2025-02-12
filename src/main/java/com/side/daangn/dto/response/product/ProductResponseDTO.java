package com.side.daangn.dto.response.product;

import com.side.daangn.entitiy.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private UUID id;
    private String title;
    private int price;
    private int only_on_sale;

    public ProductResponseDTO(Product product){
        this.id=product.getId();
        this.title=product.getTitle();
        this.price=product.getPrice();
        this.only_on_sale=product.getOnly_on_sale();
    }

}

package com.side.daangn.repository.product;

import com.side.daangn.entitiy.product.Product_Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface Product_ImageRepository extends JpaRepository<Product_Image, Long> {
    @Transactional
    List<Product_Image> findByProduct_Id(UUID productId);
}

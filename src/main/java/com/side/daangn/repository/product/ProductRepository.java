package com.side.daangn.repository.product;

import com.side.daangn.dto.request.ProductDTO;
import com.side.daangn.dto.request.SearchOptionDTO;
import com.side.daangn.dto.response.product.ProductResponseDTO;
import com.side.daangn.entitiy.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Transactional
    @Query("SELECT new com.side.daangn.dto.response.product.ProductResponseDTO(p.id, p.title, p.price, p.only_on_sale, p.image) " +
            "FROM Product p " +
            "WHERE (:search IS NULL OR p.title LIKE %:search%) " +
            "AND (:category_id IS NULL OR p.category.id = :category_id) " +
            "AND (:only_on_sale IS NULL OR p.only_on_sale = :only_on_sale) " +
            "AND (:min IS NULL OR p.price >= :min) " +
            "AND (:max IS NULL OR p.price <= :max) " +
            "ORDER BY p.updatedAt desc")
    Page<ProductResponseDTO> productList (Pageable pageable, String search, Integer category_id, Integer only_on_sale, Integer min, Integer max);

    @Transactional
    @Query("SELECT new com.side.daangn.dto.response.product.ProductResponseDTO(p.id, p.title, p.price, p.only_on_sale, p.image) " +
            "FROM Product p " +
            "WHERE p.user.id = :id " +
            "ORDER BY p.updatedAt desc")
    Page<ProductResponseDTO> userProductList(Pageable pageable, UUID id);


}

package com.side.daangn.repository.product;

import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.entitiy.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByType(int type);
    boolean existsByName(String name);
}

package com.side.daangn.service.service.product;

import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.entitiy.product.Category;

import java.util.List;

public interface CategoryService {
    boolean existsById(int id);
    List<CategoryDTO> findAll();
    String addCategory(CategoryDTO dto);
    List<CategoryDTO> findByType(String type);
}

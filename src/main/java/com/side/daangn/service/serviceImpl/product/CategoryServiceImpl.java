package com.side.daangn.service.serviceImpl.product;

import com.side.daangn.dto.response.user.CategoryDTO;
import com.side.daangn.entitiy.product.Category;
import com.side.daangn.exception.DuplicateException;
import com.side.daangn.repository.product.CategoryRepository;
import com.side.daangn.service.service.product.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public boolean existsById(int id) {
        try{
            return categoryRepository.existsById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CategoryDTO> findAll() {
        try{
            return categoryRepository.findAll().stream()
                    .map(CategoryDTO::new).toList();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public String addCategory(CategoryDTO dto) {
        try{
            if(categoryRepository.existsByName(dto.getName())){
                throw new DuplicateException("중복된 카테고리 이름");
            }
            Category category = new Category();
            category.setName(dto.getName());
            category.setType(dto.getType());
            categoryRepository.save(category);

            return "카테고리 생성";

        }catch (DuplicateException e){
            throw new DuplicateException(e.getMessage());
        }
    }

    @Override
    public List<CategoryDTO> findByType(String type) {
        try{
            if(!type.matches("\\d+")){
                return List.of();
            }
            return categoryRepository.findByType(Integer.parseInt(type)).stream()
                    .map(CategoryDTO::new).toList();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}

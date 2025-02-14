package com.side.daangn.service.serviceImpl.product;

import com.side.daangn.dto.response.product.Product_ImageDTO;
import com.side.daangn.entitiy.product.Product_Image;
import com.side.daangn.repository.product.Product_ImageRepository;
import com.side.daangn.service.service.product.Product_ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Product_ImageServiceImpl implements Product_ImageService {

    @Autowired
    private final Product_ImageRepository productImageRepository;

    @Override
    @Transactional
    public Product_Image save(Product_Image product_image) {
        try{
            return productImageRepository.save(product_image);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<Product_ImageDTO> productImageList(UUID product_id) {
        try{
            return productImageRepository.findByProduct_Id(product_id).stream()
                    .map(Product_ImageDTO::new).toList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

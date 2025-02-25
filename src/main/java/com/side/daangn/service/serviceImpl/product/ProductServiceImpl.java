package com.side.daangn.service.serviceImpl.product;

import com.side.daangn.S3.S3Service;
import com.side.daangn.dto.request.ProductDTO;
import com.side.daangn.dto.request.SearchOptionDTO;
import com.side.daangn.dto.response.product.ProductDetailDTO;
import com.side.daangn.dto.response.product.ProductResponseDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.entitiy.product.Category;
import com.side.daangn.entitiy.product.Product;
import com.side.daangn.entitiy.product.Product_Image;
import com.side.daangn.entitiy.product.Search;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.exception.NotFoundException;
import com.side.daangn.repository.product.ProductRepository;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.service.service.product.CategoryService;
import com.side.daangn.service.service.product.ProductService;
import com.side.daangn.service.service.product.Product_ImageService;
import com.side.daangn.service.service.product.SearchService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.RedisUtil;
import com.side.daangn.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final Product_ImageService productImageService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final SearchService searchService;

    @Autowired
    private final RedisUtil redisUtil;

    @Autowired
    private final S3Service s3Service;

    @Override
    public SearchPageDTO products_search(SearchOptionDTO dto) {
        try {
            int page = dto.getPage() == null || dto.getPage()<=0 ? 0 : dto.getPage()-1;
            int size = dto.getPageSize() == null || dto.getPageSize()<=0 ? 10 : dto.getPageSize();
            Pageable pageable = PageRequest.of(page, size);

            if(dto.getSearch() != null&& !dto.getSearch().isEmpty()){
                System.out.println("Search null check");
                if(redisUtil.getToken("search_"+dto.getSearch()+"_1") != null){
                    int count = Integer.parseInt(redisUtil.getToken("search_"+dto.getSearch()+"_1"));
                    redisUtil.saveToken("search_"+dto.getSearch()+"_1", String.valueOf(count+1), 60*24);
                }else{
                    redisUtil.saveToken("search_"+dto.getSearch()+"_1", "1", 60*24);
                }
            }
            Integer category_id = null;
            if(dto.getCategory_id() != null && !dto.getCategory_id().isEmpty()){
                System.out.println("Cate null check");
                String cate_str = dto.getCategory_id();
                category_id = cate_str.matches("\\d+") ? Integer.valueOf(cate_str) : null;
            }

            Integer only_on_sale = null;
            if(dto.getOnly_on_sale()!=null && ! dto.getOnly_on_sale().isEmpty()){
                if(dto.getOnly_on_sale().equals("true")){
                    only_on_sale = 1;
                } else if (dto.getOnly_on_sale().equals("false")) {
                    only_on_sale = 0;
                }
            }

            Integer min = null;
            Integer max = null;
            if(dto.getPrice()!=null&&!dto.getPrice().isEmpty()){
                String[] prc = dto.getPrice().split("__");
                if(prc.length == 2){
                    min = prc[0].matches("\\d+") ? Integer.valueOf(prc[0]) : null;
                    max = prc[1].matches("\\d+") ? Integer.valueOf(prc[1]) : null;
                }
            }
            Page<ProductResponseDTO> products = productRepository.productList(pageable, dto.getSearch(), category_id, only_on_sale, min, max);

            int maxPage = products.getTotalPages();
            long elementCount = products.getTotalElements();
            return new SearchPageDTO(page,size,maxPage,elementCount, products.getContent());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public SearchPageDTO userProductList(UUID user_id, Integer pageNum, Integer pageSize) {
        try{
            if(!userService.existsById(user_id)){
                throw new NotFoundException("찾을 수 없는 유저");
            }
            int page = pageNum == null || pageNum<=0 ? 0 : pageNum-1;
            int size = pageSize == null || pageSize<=0 ? 5 : pageSize;
            Pageable pageable = PageRequest.of(page, size);

            Page<ProductResponseDTO> products = productRepository.userProductList(pageable, user_id);
            int maxPage = products.getTotalPages();
            long elementCount = products.getTotalElements();

            return new SearchPageDTO(page,size,maxPage,elementCount, products.getContent());
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductDTO addProduct(ProductDTO productDto) {
        try{
            UUID product_id = UUID.randomUUID();
            if(!categoryService.existsById(productDto.getCategory_id())){
                throw new NotFoundException("잘못된 카테고리 선택");
            }
            //파일 검즘하고 aws s3에 이미지 업로드 -> DB에 URL 저장.

            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setBody(productDto.getBody());
            product.setPrice(productDto.getPrice());

            UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
            User user = new User();
            user.setId(userService.findById(userPrincipal.getId()).getId());
            product.setUser(user);

            Category category = new Category();
            category.setId(productDto.getCategory_id());
            product.setCategory(category);

            product.setPrice(productDto.getPrice());

            product.setImage(productDto.getImgList().get(0));

            product = productRepository.save(product);


            for(String imgName : productDto.getImgList()){
                Product_Image product_image = new Product_Image();
                product_image.setProduct(product);
                product_image.setFileName(imgName);
                productImageService.save(product_image);
            }

            return new ProductDTO(product);

        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductDetailDTO productDetail(UUID id) {
        try{
            Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("찾을 수 없는 제품"));
            return new ProductDetailDTO(product);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    public List<String> uploadProductImg(List<MultipartFile> files) {
        try {
            if(files.isEmpty()){
                throw new IllegalArgumentException("1개 이상의 상품 사진을 등록해 주세요.");
            }
            List<String> imgList = new ArrayList<>();
            for(MultipartFile file : files){
                String fileName = s3Service.uploadImage(file, UUID.randomUUID());
                imgList.add(fileName);
            }
            return imgList;
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("product 파일 업로드");
        }
    }
}

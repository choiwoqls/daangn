package com.side.daangn.service.serviceImpl.user;

import com.side.daangn.S3.S3Service;
import com.side.daangn.dto.request.auth.SignUpDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.exception.DuplicateException;
import com.side.daangn.exception.NotFoundException;
import com.side.daangn.exception.UnauthorizedException;
import com.side.daangn.repository.user.UserRepository;
import com.side.daangn.security.JwtTokenProvider;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.service.service.product.ProductService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.HashUtil;
import com.side.daangn.util.RedisUtil;
import com.side.daangn.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private final RedisUtil redisUtil;

    @Autowired
    private final S3Service s3Service;


    @Override
    public UserDTO findByEmail(String email) {
        try {
           User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("찾을 수 없는 이메일"));
           return new UserDTO(user);
       }catch (NotFoundException e){
           throw new NotFoundException(e.getMessage());
       }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDTO findById(UUID id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("찾을 수 없는 유저 ID"));
            return new UserDTO(user);
       }catch (NotFoundException e){
           throw new NotFoundException(e.getMessage());
       }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try{
            return userRepository.existsByEmail(email);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        try{
            return userRepository.existsById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        try{
            return userRepository.existsByName(name);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String logout() {
        try{

            UserPrincipal userPrincipal = null;
            UserDTO userDto = null;

            userPrincipal = UserUtils.getLoggedInUser();
            userDto = this.findById(userPrincipal.getId());

            String id = String.valueOf(userDto.getId());

            redisUtil.deleteToken(id);
            SecurityContextHolder.clearContext();

            return "로그아웃.";

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Transactional
    @Override
    public String signUp(SignUpDTO dto, MultipartFile file, boolean check) {
        double temp = 36.5;
        UUID img_id = UUID.randomUUID();
        try {
            if(userRepository.existsByEmail(dto.getEmail())){
                throw new DuplicateException("이미 사용중인 이메일");
            }
            if(userRepository.existsByName(dto.getName())){
                throw new DuplicateException("이미 사용중인 닉네임");
            }
            if(check){
                redisUtil.matchedToken(dto.getEmail(), dto.getCode());
                redisUtil.deleteToken(dto.getEmail());
            }

            String image = "8983cc4d-f7c2-4471-967c-387dd9ac5967.png";


            if(!file.isEmpty()){
                image = s3Service.uploadImage(file, img_id);
            }

            User user = new User();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPassword(HashUtil.hashPassword(dto.getPassword()));
            user.setTemp(temp);
            user.setImage(image);
            userRepository.save(user);
            return "회원 가입 성공";

        }catch (DuplicateException e){
            throw new DuplicateException(e.getMessage());
        }catch (UnauthorizedException e){
            throw new UnauthorizedException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}

package com.side.daangn.service.service.user;

import com.side.daangn.dto.request.auth.SignUpDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.dto.response.user.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {

    String signUp(SignUpDTO dto, MultipartFile file, boolean check);


    UserDTO findByEmail(String email);

    UserDTO findById(UUID id);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    boolean existsByName(String name);

    String logout();


}

package com.side.daangn.service.service.user;

import com.side.daangn.dto.request.auth.SignUpDTO;
import com.side.daangn.dto.response.user.SearchPageDTO;
import com.side.daangn.dto.response.user.UserDTO;

import java.util.UUID;

public interface UserService {

    String signUp(SignUpDTO dto);

    UserDTO findByEmail(String email);

    UserDTO findById(UUID id);

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    String logout();


}

package com.side.daangn.service.service.user;

import com.side.daangn.dto.request.SignUpDTO;
import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.entitiy.user.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    String signUp(SignUpDTO dto);

    UserDTO findByEmail(String email);

    UserDTO findById(UUID id);

    boolean existsByEmail(String email);

    String logout();

}

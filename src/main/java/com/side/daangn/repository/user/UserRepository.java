package com.side.daangn.repository.user;

import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.entitiy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByEmail(String email);

    User findByName(String name);



}

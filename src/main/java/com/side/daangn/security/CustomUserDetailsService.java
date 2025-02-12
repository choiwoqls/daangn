package com.side.daangn.security;

import com.side.daangn.entitiy.user.User;
import com.side.daangn.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional
    public UserDetails loadByUserId(UUID id) {
        System.out.println("123");
        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User를 찾을 수 없습니다. id: " + id));
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("1231");
        User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User를 찾을 수 없습니다. email: " + username));
        return UserPrincipal.create(user);
    }
}

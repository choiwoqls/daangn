package com.side.daangn.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.side.daangn.entitiy.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
//@AllArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {

    private UUID id;
    private String email;
    @JsonIgnore
    private String password;
    private String name;

    private Map<String, Object> attributes;

    public UserPrincipal(UUID id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserPrincipal(UUID id, String email, String password, String name, Map<String, Object> attributes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.attributes = attributes;
    }


     public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName());
    }

     public static UserPrincipal create(User user, Map<String, Object> attributes) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword(){
         return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

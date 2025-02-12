package com.side.daangn.service.serviceImpl.user;

import com.side.daangn.dto.request.LoginDTO;
import com.side.daangn.dto.response.user.UserDTO;
import com.side.daangn.entitiy.user.User;
import com.side.daangn.exception.IncorrectPasswordException;
import com.side.daangn.exception.NotFoundException;
import com.side.daangn.exception.UnauthorizedException;
import com.side.daangn.security.JwtTokenProvider;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.service.service.user.AuthService;
import com.side.daangn.service.service.user.UserService;
import com.side.daangn.util.HashUtil;
import com.side.daangn.util.JWTAuthenticationResponse;
import com.side.daangn.util.JwtUtil;
import com.side.daangn.util.RedisUtil;
import com.side.daangn.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final RedisUtil redisUtil;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public JWTAuthenticationResponse login(LoginDTO loginDTO) {
        try{
            String email = loginDTO.getEmail();
            String password = loginDTO.getPassword();

            UserDTO user = userService.findByEmail(email);

            if(!HashUtil.isTheSame(password, user.getPassword())){
                 throw new IncorrectPasswordException("일치하지 않는 비밀번호");
            }

            String jwt = null;
            UserPrincipal userPrincipal = null;
            UserDTO userDto = null;

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            Authentication authentication = authenticationProvider.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwt = jwtTokenProvider.generateToken(authentication);
            userPrincipal = UserUtils.getLoggedInUser();
            userDto = userService.findById(userPrincipal.getId());

            redisUtil.saveToken(String.valueOf(userDto.getId()), jwt, 60);
            return new JWTAuthenticationResponse(jwt);
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (IncorrectPasswordException e){
            throw new IncorrectPasswordException(e.getMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String checkCode(String email, String code) {
        try {
            redisUtil.matchedToken(email, code);
            redisUtil.deleteToken(email);
            redisUtil.saveToken(email+"_success","success",10);
            return "인증 성공";
        }catch (UnauthorizedException e){
            throw new UnauthorizedException("코드를 다시 확인해 주세요.");
        }catch (NullPointerException e){
            throw new NotFoundException("발급받은 코드가 없거나 유효기간이 만료되었습니다.");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}

package com.side.daangn.oauth2;

import com.side.daangn.entitiy.user.User;
import com.side.daangn.exception.DuplicateException;
import com.side.daangn.repository.user.UserRepository;
import com.side.daangn.security.UserPrincipal;
import com.side.daangn.util.HashUtil;
import com.side.daangn.util.NickNameUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("hi kakao");

        System.out.println("getClientRegistration = " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인 했는지 확인 가능
        System.out.println("getAccessToken = " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("getAttributes = " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("우리는 카카오만 지원합니다.");
        }

        String provider =  oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String name = NickNameUtil.randonName();
        while(userRepository.existsByName(name)){
            name = NickNameUtil.randonName();
        }
        //임시 password 생성 -> mail로 전송.
        String password = HashUtil.hashPassword("password123!");
        String email = oAuth2UserInfo.getEmail();

        User userEntity = userRepository.findByEmail(email).orElse(null);

        if(userEntity == null){
            //oauth 로그인 최초 로그인
            System.out.println("회원가입");
            userEntity = new User();
            userEntity.setName(name);
            userEntity.setEmail(email);
            userEntity.setTemp(36.5);
            userEntity.setPassword(password);
            userEntity.setProvider(provider);
            userEntity.setProviderId(providerId);
            userEntity.setImage("8983cc4d-f7c2-4471-967c-387dd9ac5967.png");
            userRepository.save(userEntity);
        }else{
            try {
                if(userEntity.getProvider()==null){
                    throw new DuplicateException("이미 이메일로 가입된 계정입니다.");
                }
            }catch (DuplicateException e){
                throw new DuplicateException(e.getMessage());
            }
        }

        //jwt  ..? redis ??
        return UserPrincipal.create(userEntity, oAuth2User.getAttributes());
    }
}

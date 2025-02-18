package com.side.daangn.util;

import com.side.daangn.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserUtils {
    public static boolean isUserLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return true;
        }else{
            return false;
        }
    }
    public static UserPrincipal getLoggedInUser() {
        // Retrieve the currently authenticated user from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("확인 : " + authentication.getPrincipal().getClass());
            return (UserPrincipal) authentication.getPrincipal();
        }
        return null;
    }


}

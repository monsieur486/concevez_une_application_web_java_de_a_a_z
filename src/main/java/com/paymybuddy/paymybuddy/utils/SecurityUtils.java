package com.paymybuddy.paymybuddy.utils;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private UserService userService;

    public static User getUserAuthtificated() {
        User user = new User();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = (User) authentication.getPrincipal();

        }
        return user;
    }
}


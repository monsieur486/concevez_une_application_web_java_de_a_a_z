package com.paymybuddy.paymybuddy.security;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.MessageRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.MessageService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService service;

    @Mock
    UserRepository dao;

    @Test
    void loadUserByUsername() {
        User user = new User(1L,"demo@test.fr", "pwd",0);
        when(dao.findByEmail(any(String.class))).thenReturn(user);
        UserDetails userDetails = service.loadUserByUsername("demo@test.fr");
        assertEquals("demo@test.fr", userDetails.getUsername());
    }
}
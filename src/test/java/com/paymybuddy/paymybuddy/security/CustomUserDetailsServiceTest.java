package com.paymybuddy.paymybuddy.security;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService service;

    @Mock
    UserRepository dao;

    @Test
    void loadUserByUsername() {
        User user = new User(1L, "demo@test.fr", "pwd", 0);
        when(dao.findByEmail("demo@test.fr")).thenReturn(user);
        UserDetails userDetails = service.loadUserByUsername("demo@test.fr");
        assertEquals("demo@test.fr", userDetails.getUsername());
    }

    @Test
    void loadUserByInvalidUsernameAndTrowUsernameNotFoundException() {
        when(dao.findByEmail(any())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("demo@test.fr");
        });
    }
}
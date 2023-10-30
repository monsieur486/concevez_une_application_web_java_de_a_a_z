package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.UserFormDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository dao;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void saveUser() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setEmail("demo@test.fr");
        userFormDto.setPassword("pwd");
        User user = new User();
        user.setEmail(userFormDto.getEmail());
        user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        when(dao.existsByEmail("demo@test.fr")).thenReturn(false);
        service.saveUser(userFormDto);
        verify(dao, times(1)).save(any(User.class));
    }

    @Test
    void saveUserFailsBecauseItsExists() {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setEmail("demo@test.fr");
        userFormDto.setPassword("pwd");
        User user = new User();
        user.setEmail(userFormDto.getEmail());
        user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        when(dao.existsByEmail("demo@test.fr")).thenReturn(true);
        service.saveUser(userFormDto);
        verify(dao, times(0)).save(any(User.class));
    }

    @Test
    void findByEmail() {
        User user = new User(1L,"demo@test.fr","pwd",0);
        when(dao.findByEmail("demo@test.fr")).thenReturn(user);
        assertEquals(user,service.findByEmail("demo@test.fr"));
    }

    @Test
    void setBalance() {
        User user = new User(1L,"demo@test.fr","pwd",0);
        service.setBalance(user,100);
        verify(dao, times(1)).save(any(User.class));
    }

    @Test
    void alreadyExist() {
        User user = new User(1L,"demo@test.fr","pwd",0);
        when(dao.existsByEmail("demo@test.fr")).thenReturn(true);
        assertTrue(service.alreadyExist("demo@test.fr"));
    }
}
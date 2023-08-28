package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            log.error("User already exist : {}", userDto.getEmail());
            return;
        }
        log.info("User created : {}", userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        log.debug("Find user by email : {}", email);
        return userRepository.findByEmail(email);
    }
}

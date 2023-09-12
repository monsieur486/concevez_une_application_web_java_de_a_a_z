package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userDto.getEmail()))) {
            log.error("User already exist : {}", userDto.getEmail());
            return;
        }
        userRepository.save(user);
        log.info("User created : {}", userDto.getEmail());
    }

    public User findByEmail(String email) {
        log.debug("Find user by email : {}", email);
        return userRepository.findByEmail(email);
    }

    public void setBalance(User user, Integer balance) {
        user.setBalance(balance);
        userRepository.save(user);
        log.info("User balance updated : {}", user);
    }
}

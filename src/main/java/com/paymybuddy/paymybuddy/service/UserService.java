package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.UserFormDto;
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

    public void saveUser(UserFormDto userFormDto) {
        User user = new User();
        user.setEmail(userFormDto.getEmail());
        user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userFormDto.getEmail()))) {
            log.error("User already exist : {}", userFormDto.getEmail());
            return;
        }
        userRepository.save(user);
        log.info("User created : {}", userFormDto.getEmail());
    }

    public User findByEmail(String email) {
        log.debug("Find user by email : {}", email);
        return userRepository.findByEmail(email);
    }

    public void setBalance(User user, Integer balance) {
        user.setBalance(balance);
        userRepository.save(user);
        log.info("User {} balance updated : {}", user, balance);
    }

    public Boolean alreadyExist(String email) {
        log.debug("Check if user already exist : {}", email);
        return userRepository.existsByEmail(email);
    }
}

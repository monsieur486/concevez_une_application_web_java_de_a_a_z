package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.form.UserFormDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user related operations.
 */
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService.
     *
     * @param userRepository   the user repository
     * @param passwordEncoder  the password encoder
     */
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new user to the repository.
     *
     * @param userFormDto  the user form data transfer object
     */
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

    /**
     * Finds a user by email.
     *
     * @param email  the email of the user
     * @return       the user object
     */
    public User findByEmail(String email) {
        log.debug("Find user by email : {}", email);
        return userRepository.findByEmail(email);
    }

    /**
     * Sets the balance for a user.
     *
     * @param user     the user object
     * @param balance  the balance to be set
     */
    public void setBalance(User user, Integer balance) {
        user.setBalance(balance);
        userRepository.save(user);
        log.info("User {} balance updated : {}", user, balance);
    }

    /**
     * Checks if a user already exists by email.
     *
     * @param email  the email of the user
     * @return       true if the user exists, false otherwise
     */
    public Boolean alreadyExist(String email) {
        log.debug("Check if user already exist : {}", email);
        return userRepository.existsByEmail(email);
    }
}

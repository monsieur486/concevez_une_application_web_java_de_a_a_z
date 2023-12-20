package com.paymybuddy.paymybuddy.security;

import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

/**
 * CustomUserDetailsService class that implements UserDetailsService interface.
 * This class is used to load user-specific data.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // UserRepository instance
    private final UserRepository userRepository;

    /**
     * Constructor for CustomUserDetailsService.
     * @param userRepository UserRepository instance
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Overridden method from UserDetailsService interface.
     * This method is used to find user by email and load user-specific data.
     * @param email User's email
     * @return UserDetails instance
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    /**
     * This method is used to map user roles to authorities.
     * @return Collection of GrantedAuthority
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        return new HashSet<>();
    }
}
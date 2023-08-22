package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.Role;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.RoleRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${SUPERUSER_NAME}")
    private String superUserName;

    @Value("${SUPERUSER_PASSWORD}")
    private String superUserPassword;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist("ROLE_USER");
        }
        user.setRoles(List.of(role));
        log.info("User created : {}", userDto.getEmail());
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        log.debug("Find user by email : {}", email);
        return userRepository.findByEmail(email);
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        log.debug("Role {} created", roleName);
        return roleRepository.save(role);
    }

    public void saveSuperUser() {
        User superUser = new User();
        superUser.setName("Super User");
        superUser.setEmail(superUserName);
        superUser.setPassword(passwordEncoder.encode(superUserPassword));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist("ROLE_ADMIN");
        }
        superUser.setRoles(List.of(role));
        userRepository.save(superUser);
        log.warn("Super user created !!!");
    }

    public void createSuperUser() {
        User superUser = findByEmail(superUserName);
        if(superUser == null){
            saveSuperUser();
        }
    }
}

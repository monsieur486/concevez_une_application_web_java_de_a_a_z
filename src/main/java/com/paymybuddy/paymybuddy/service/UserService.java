package com.paymybuddy.paymybuddy.service;



import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}

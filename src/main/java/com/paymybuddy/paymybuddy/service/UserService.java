package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);
}

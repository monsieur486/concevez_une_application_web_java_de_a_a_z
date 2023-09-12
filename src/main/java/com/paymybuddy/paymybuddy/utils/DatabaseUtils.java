package com.paymybuddy.paymybuddy.utils;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.ConnectionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {
    private final UserService userService;
    private final ConnectionService connectionService;

    public DatabaseUtils(UserService userService, ConnectionService connectionService) {
        this.userService = userService;
        this.connectionService = connectionService;
    }

    private void addDemoUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("demo@user.fr");
        userDto.setPassword("Mdp12345");
        userService.saveUser(userDto);
    }

    private void addUsers() {
        User userDemo = userService.findByEmail("demo@user.fr");
        for (int i = 0; i < 10; i++) {
            UserDto userDto = new UserDto();
            userDto.setEmail(FakeData.getEmail());
            userDto.setPassword("Mdp12345");
            userService.saveUser(userDto);
            User friend = userService.findByEmail(userDto.getEmail());
            connectionService.addConnection(userDemo, friend, "Nickname" + i+1);
        }
    }

    public void createFakeData(){
        addDemoUser();
        addUsers();
    }
}

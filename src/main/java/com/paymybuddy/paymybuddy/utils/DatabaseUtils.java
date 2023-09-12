package com.paymybuddy.paymybuddy.utils;

import com.paymybuddy.paymybuddy.dto.UserDto;
import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.ConnectionService;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {
    private final UserService userService;
    private final ConnectionService connectionService;

    private final TransactionService transactionService;

    public DatabaseUtils(UserService userService, ConnectionService connectionService, TransactionService transactionService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.transactionService = transactionService;
    }

    private void addDemoUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("demo@user.fr");
        userDto.setPassword("Mdp12345");
        userService.saveUser(userDto);
    }

    private void addUsers() {
        User userDemo = userService.findByEmail("demo@user.fr");
        userService.setBalance(userDemo, 1000);
        int count = 0;
        for (int i = 1; i < 11; i++) {
            UserDto userDto = new UserDto();
            userDto.setEmail(FakeData.getEmail());
            userDto.setPassword("Mdp12345");
            userService.saveUser(userDto);
            User friend = userService.findByEmail(userDto.getEmail());
            Connection connection = connectionService.addConnection(userDemo, friend, "Nickname" + i);
            for (int j = 1; j < 4; j++) {
                count++;
                transactionService.addTransaction(connection, j * 10, "Transaction " + count);
            }
        }
    }

    public void createFakeData() {
        addDemoUser();
        addUsers();
    }
}

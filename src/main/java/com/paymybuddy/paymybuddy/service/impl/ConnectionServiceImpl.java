package com.paymybuddy.paymybuddy.service.impl;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.service.ConnectionService;
import com.paymybuddy.paymybuddy.service.UserService;

import java.util.List;

public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionService connectionService;
    private final UserService userService;

    public ConnectionServiceImpl(ConnectionService connectionService, UserService userService) {
        this.connectionService = connectionService;
        this.userService = userService;
    }

    @Override
    public void addConnection(String email, String emailConnection) {
        Connection connection = new Connection();
        connection.setUser(userService.findByEmail(email));
        connection.setUserConnection(userService.findByEmail(emailConnection));
        connectionService.addConnection(email, emailConnection);
    }

    @Override
    public void deleteConnection(String email, String emailConnection) {
        connectionService.deleteConnection(email, emailConnection);
    }

    @Override
    public void deleteAllConnectionsByUserConnection(String email) {
        connectionService.deleteAllConnectionsByUserConnection(email);
    }

    @Override
    public Boolean existByConnection(Connection connection) {
        return connectionService.existByConnection(connection);
    }

    @Override
    public List<Connection> findAllByUser(String email) {
        return connectionService.findAllByUser(email);
    }

    @Override
    public Connection findByUserAndUserConnection(String email, String emailConnection) {
        return connectionService.findByUserAndUserConnection(email, emailConnection);
    }

    @Override
    public Connection saveConnection(Connection connection) {
        if (Boolean.TRUE.equals(connectionService.existByConnection(connection))) {
            return null;
        } else {
            return connectionService.saveConnection(connection);
        }
    }

    @Override
    public Connection findById(Long id) {
        return connectionService.findById(id);
    }
}

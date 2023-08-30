package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;

import java.util.List;

public class ConnectionService {

    private final ConnectionService connectionService;
    private final UserService userService;

    public ConnectionService(ConnectionService connectionService, UserService userService) {
        this.connectionService = connectionService;
        this.userService = userService;
    }

    public void addConnection(String email, String emailConnection) {
        Connection connection = new Connection();
        connection.setUser(userService.findByEmail(email));
        connection.setUserConnected(userService.findByEmail(emailConnection));
        connectionService.addConnection(email, emailConnection);
    }

    public void deleteConnection(String email, String emailConnection) {
        connectionService.deleteConnection(email, emailConnection);
    }

    public void deleteAllConnectionsByUserConnection(String email) {
        connectionService.deleteAllConnectionsByUserConnection(email);
    }

    public Boolean existByConnection(Connection connection) {
        return connectionService.existByConnection(connection);
    }

    public List<Connection> findAllByUser(String email) {
        return connectionService.findAllByUser(email);
    }

    public Connection findByUserAndUserConnection(String email, String emailConnection) {
        return connectionService.findByUserAndUserConnection(email, emailConnection);
    }

    public Connection saveConnection(Connection connection) {
        if (Boolean.TRUE.equals(connectionService.existByConnection(connection))) {
            return null;
        } else {
            return connectionService.saveConnection(connection);
        }
    }

    public Connection findById(Long id) {
        return connectionService.findById(id);
    }
}

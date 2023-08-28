package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;

import java.util.List;

public interface ConnectionService {
    void addConnection(String email, String emailConnection);

    void deleteConnection(String email, String emailConnection);

    void deleteAllConnectionsByUserConnection(String email);

    Boolean existByConnection(Connection connection);

    List<Connection> findAllByUser(String email);

    Connection findByUserAndUserConnection(String email, String emailConnection);

    Connection saveConnection(Connection connection);

    Connection findById(Long id);

}

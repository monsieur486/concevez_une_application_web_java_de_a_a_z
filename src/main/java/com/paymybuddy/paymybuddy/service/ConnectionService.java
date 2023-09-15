package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.ConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public Connection addConnection(User user, User friend, String nickname) {
        Connection connection = new Connection();
        connection.setUser(user);
        connection.setUserConnected(friend);
        connection.setNickname(nickname);
        log.info("Connection added : {}", connection);
        return connectionRepository.save(connection);
    }

    public List<Connection> getConnections(User user) {
        return connectionRepository.findByUserOrderByNickname(user);
    }

    public Page<Connection> getPageConnections(User user, int page, int size) {
        return connectionRepository.findByUserOrderByNickname(user, PageRequest.of(page, size));
    }
}

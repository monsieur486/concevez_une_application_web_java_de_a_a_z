package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.ConnectionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling connection related operations.
 */
@Service
@Slf4j
@Transactional
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    /**
     * Constructor for ConnectionService.
     *
     * @param connectionRepository  the connection repository
     */
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    /**
     * Adds a new connection to the repository.
     *
     * @param user      the user object
     * @param friend    the friend object
     * @param nickname  the nickname of the friend
     */
    public void addConnection(User user, User friend, String nickname) {
        Connection connection = new Connection();
        connection.setUser(user);
        connection.setUserConnected(friend);
        connection.setNickname(nickname);
        connectionRepository.save(connection);
    }

    /**
     * Retrieves a list of connections for a user.
     *
     * @param user  the user object
     * @return      a list of connections
     */
    public List<Connection> getConnections(User user) {
        return connectionRepository.findByUserOrderByNickname(user);
    }

    /**
     * Retrieves a page of connections for a user.
     *
     * @param user  the user object
     * @param page  the page number
     * @param size  the page size
     * @return      a page of connections
     */
    public Page<Connection> getPageConnections(User user, int page, int size) {
        return connectionRepository.findByUserOrderByNickname(user, PageRequest.of(page, size));
    }

    /**
     * Deletes a connection by id.
     *
     * @param id  the id of the connection
     */
    public void deleteConnection(Long id) {
        connectionRepository.deleteById(id);
    }

    /**
     * Checks if a connection already exists between a user and a friend.
     *
     * @param user   the user object
     * @param friend the friend object
     * @return       true if the connection exists, false otherwise
     */
    public Boolean existConnectionByFriend(User user, User friend) {
        return connectionRepository.findByUserAndUserConnected(user, friend) != null;
    }

    /**
     * Finds a connection by id.
     *
     * @param connectionId  the id of the connection
     * @return              the connection object
     */
    public Connection findById(Long connectionId) {
        return connectionRepository.findById(connectionId).orElse(null);
    }
}

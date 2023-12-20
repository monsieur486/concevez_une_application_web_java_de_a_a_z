package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ConnectionRepository is an interface that extends CrudRepository.
 * It provides methods to perform CRUD operations on Connection entities.
 * It also provides methods to find connections by user and order them by nickname.
 */
@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Long> {

    /**
     * Finds all connections of a user and orders them by nickname.
     *
     * @param user the user whose connections are to be found
     * @return a list of connections ordered by nickname
     */
    List<Connection> findByUserOrderByNickname(User user);

    /**
     * Finds all connections of a user, orders them by nickname, and returns a page of results.
     *
     * @param user     the user whose connections are to be found
     * @param pageable the pagination information
     * @return a page of connections ordered by nickname
     */
    Page<Connection> findByUserOrderByNickname(User user, Pageable pageable);

    /**
     * Finds a connection by its id.
     *
     * @param id the id of the connection to be found
     * @return the connection with the given id
     */
    Connection findById(long id);

    /**
     * Deletes a connection by its id.
     *
     * @param id the id of the connection to be deleted
     */
    void deleteById(long id);

    /**
     * Finds a connection between a user and a friend.
     *
     * @param user   the user
     * @param friend the friend
     * @return the connection between the user and the friend
     */
    Connection findByUserAndUserConnected(User user, User friend);
}
package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.repository.ConnectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {

    @InjectMocks
    ConnectionService service;

    @Mock
    ConnectionRepository dao;


    @Test
    void addConnection() {
        User user = new User(1L, "user@test.fr", "pwd", 0);
        User friend = new User(2L, "friend@test.fr", "pwd", 0);
        service.addConnection(user, friend, "nickname");
        verify(dao, times(1)).save(any(Connection.class));
    }

    @Test
    void getConnections() {
        User user = new User(1L, "user@test.fr", "pwd", 0);
        service.getConnections(user);
        verify(dao, times(1)).findByUserOrderByNickname(user);
    }

    @Test
    void getPageConnections() {
        User user = new User(1L, "user@test.fr", "pwd", 0);
        when(dao.findByUserOrderByNickname(any(User.class), any(Pageable.class))).thenReturn(Page.empty());
        service.getPageConnections(user, 0, 10);
        verify(dao, times(1)).findByUserOrderByNickname(user, PageRequest.of(0, 10));

    }

    // test deleteConnection with mockito
    @Test
    void deleteConnection() {
        User user = new User(1L, "user@test.fr", "pwd", 0);
        User friend = new User(2L, "friend@test.fr", "pwd", 0);
        Connection connection = new Connection();
        connection.setId(1L);
        connection.setUser(user);
        connection.setUserConnected(friend);
        connection.setNickname("nickname");
        service.deleteConnection(connection.getId());
        verify(dao, times(1)).deleteById(connection.getId());
    }

    @Test
    void existConnectionByFriend() {
        User user = new User(1L, "user@test.fr", "pwd", 0);
        User friend = new User(2L, "friend@test.fr", "pwd", 0);
        Connection connection = new Connection();
        connection.setId(1L);
        connection.setUser(user);
        connection.setUserConnected(friend);
        connection.setNickname("nickname");

        when(dao.findByUserAndUserConnected(user, friend)).thenReturn(connection);
        assertTrue(service.existConnectionByFriend(user, friend));
    }

    @Test
    void findById() {
        User user = new User(1L, "user@test.fr", "pwd", 0);
        User friend = new User(2L, "friend@test.fr", "pwd", 0);
        Connection connection = new Connection();
        connection.setId(1L);
        connection.setUser(user);
        connection.setUserConnected(friend);
        connection.setNickname("nickname");
        when(dao.findById(any(Long.class))).thenReturn(java.util.Optional.of(connection));

        assertEquals(1L, service.findById(1L).getId());
    }
}
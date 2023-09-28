package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.ConnectionPageDto;
import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class ConnectionPageDtoService {

    private final UserService userService;

    private final ConnectionService connectionService;

    private final TransactionService transactionService;

    public ConnectionPageDtoService(UserService userService, ConnectionService connectionService, TransactionService transactionService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.transactionService = transactionService;
    }

    public ConnectionPageDto createConnectionPageDto(String principal,
                                                     Optional<Integer> page,
                                                     Optional<Integer> size) {
        ConnectionPageDto connectionPageDto = new ConnectionPageDto();

        int currentPage = page.orElse(1);
        if (currentPage < 1) currentPage = 1;
        int pageSize = size.orElse(5);

        User userDB = userService.findByEmail(principal);
        connectionPageDto.setUserConnect(userDB);

        Page<Connection> connectionsDB = connectionService.getPageConnections(userDB, currentPage - 1, pageSize);
        if (connectionsDB != null) {
            int totalPages = connectionsDB.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .toList();
                connectionPageDto.setPageNumbers(pageNumbers);
                connectionPageDto.setCurrentPage(currentPage);
                connectionPageDto.setTotalPage(totalPages);
            }
            connectionPageDto.setConnections(connectionsDB);
        } else {
            connectionPageDto.setConnections(null);
        }

        return connectionPageDto;

    }

    public void deleteConnection(Long id) {
        transactionService.deleteTransactionsByConnectionId(id);
        connectionService.deleteConnection(id);
    }
}

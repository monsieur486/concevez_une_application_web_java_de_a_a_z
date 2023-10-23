package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.ConnectionDto;
import com.paymybuddy.paymybuddy.dto.ProfilePageDto;
import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProfilePageService {

    private final UserService userService;
    private final ConnectionService connectionService;
    private final TransactionService transactionService;

    public ProfilePageService(UserService userService, ConnectionService connectionService, TransactionService transactionService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.transactionService = transactionService;
    }

    public void deleteConnection(Long id) {
        transactionService.deleteTransactionsByConnectionId(id);
        connectionService.deleteConnection(id);
        log.info("Connection deleted id: {}", id);
    }

    public void addConnection(String user, ConnectionDto connectionDto) {
        User userDB = userService.findByEmail(user);
        User userConnectedDB = userService.findByEmail(connectionDto.getEmail());
        String nickname = connectionDto.getNickname();
        connectionService.addConnection(userDB, userConnectedDB, nickname);
        log.info("Connection added: {}", connectionDto);
    }

    public Boolean friendAlreadyExists(String user) {
        return userService.alreadyExist(user);
    }

    public Boolean alreadyExistsByFriend(String user, String friend) {
        User userDB = userService.findByEmail(user);
        User friendDB = userService.findByEmail(friend);
        return connectionService.existConnectionByFriend(userDB, friendDB);
    }

    public ProfilePageDto renderProfilePage(String principal,
                                            Optional<Integer> page,
                                            Optional<Integer> size,
                                            ConnectionDto connectionDto) {

        ProfilePageDto profilePageDto = new ProfilePageDto();

        int currentPage = page.orElse(1);
        if (currentPage < 1) currentPage = 1;
        int pageSize = size.orElse(5);

        User userDB = userService.findByEmail(principal);
        profilePageDto.setUserConnected(userDB.getEmail());

        if (connectionDto == null) {
            connectionDto = new ConnectionDto();
        }
        profilePageDto.setConnectionForm(connectionDto);

        Page<Connection> connectionsDB = connectionService.getPageConnections(userDB, currentPage - 1, pageSize);
        profilePageDto.setConnections(connectionsDB);

        List<Integer> pageNumbers;
        if (connectionsDB.getTotalPages() > 0) {
            pageNumbers = IntStream.rangeClosed(1, connectionsDB.getTotalPages())
                    .boxed()
                    .toList();
            profilePageDto.setPageNumbers(pageNumbers);
            profilePageDto.setCurrentPage(currentPage);
            profilePageDto.setTotalPage(connectionsDB.getTotalPages());
        }
        profilePageDto.setConnections(connectionsDB);

        return profilePageDto;
    }
}

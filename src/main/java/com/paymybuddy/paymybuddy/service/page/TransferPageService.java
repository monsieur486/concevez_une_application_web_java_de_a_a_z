package com.paymybuddy.paymybuddy.service.page;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import com.paymybuddy.paymybuddy.dto.form.TransactionFormDto;
import com.paymybuddy.paymybuddy.dto.page.TransferPageDto;
import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import com.paymybuddy.paymybuddy.service.ConnectionService;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.tools.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Slf4j
public class TransferPageService {

    private final UserService userService;

    private final TransactionService transactionService;

    private final ConnectionService connectionService;

    public TransferPageService(UserService userService, TransactionService transactionService, ConnectionService connectionService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.connectionService = connectionService;
    }

    public TransferPageDto createTransferPageDto(
            TransactionFormDto transactionForm,
            String userMail,
            Optional<Integer> page,
            Optional<Integer> size) {

        TransferPageDto transferPageDto = new TransferPageDto();

        int currentPage = page.orElse(1);
        if (currentPage < 1) currentPage = 1;
        int pageSize = size.orElse(ApplicationConfiguration.NUMBER_OF_TRANSACTIONS_TO_DISPLAY);

        User userDB = userService.findByEmail(userMail);
        transferPageDto.setSolde(StringUtil.convertCentsInMoney(userDB.getBalance()));

        if (transactionForm == null) {
            log.warn("transactionForm is null - new ceation");
            transactionForm = new TransactionFormDto();

        }

        List<Connection> connectionsDB = connectionService.getConnections(userDB);
        transactionForm.setConnections(connectionsDB);

        transferPageDto.setTransactionForm(transactionForm);

        Page<Transaction> transactionsDB = transactionService.getTransactions(userDB, currentPage - 1, pageSize);
        if (transactionsDB != null) {
            int totalPages = transactionsDB.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .toList();
                transferPageDto.setPageNumbers(pageNumbers);
                transferPageDto.setCurrentPage(currentPage);
                transferPageDto.setTotalPage(totalPages);
            }
            transferPageDto.setTransactions(transactionsDB);
        } else {
            transferPageDto.setTransactions(null);
        }

        return transferPageDto;
    }

    public void addTransaction(TransactionFormDto transactionForm, String userMail) {
        User userDB = userService.findByEmail(userMail);
        Connection connectionDB = connectionService.findById(transactionForm.getConnectionId());
        User friendDB = connectionDB.getUserConnected();
        Integer amount = transactionForm.getAmount() * 100;
        Integer fee = (int) (amount * ApplicationConfiguration.TRANSACTION_FEE_PERCENTAGE);
        Integer balanceUserDB = userDB.getBalance() - (amount + fee);
        Integer balanceFriendDB = friendDB.getBalance() + amount;

        userDB.setBalance(balanceUserDB);
        friendDB.setBalance(balanceFriendDB);

        transactionService.addTransaction(connectionDB, transactionForm.getAmount(), transactionForm.getDescription());

    }

    public Boolean balanceIsSufficient(String userEmail, Integer amount) {
        User userDB = userService.findByEmail(userEmail);
        Integer balance = userDB.getBalance();
        int amountInCents = amount * 100;
        double fee = amountInCents * ApplicationConfiguration.TRANSACTION_FEE_PERCENTAGE;
        Integer totalTransaction = amountInCents + (int) fee;

        return balance >= totalTransaction;
    }

    public Integer maximumAmount(String userEmail) {
        User userDB = userService.findByEmail(userEmail);
        Integer balance = userDB.getBalance();
        double fee = balance * ApplicationConfiguration.TRANSACTION_FEE_PERCENTAGE;
        return (int) (balance - fee);
    }
}

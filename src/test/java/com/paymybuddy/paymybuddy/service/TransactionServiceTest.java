package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService service;

    @Mock
    TransactionRepository dao;

    @Test
    void addTransaction() {
        service.addTransaction(null, 0, null);
        when(dao.save(any())).thenReturn(null);
        assertNull(dao.save(any()));
    }

    @Test
    void getTransactions() {
        service.getTransactions(null, 0, 10);
        when(dao.findAllByConnection_UserOrderByIdDesc(any(), any())).thenReturn(null);
        assertNull(dao.findAllByConnection_UserOrderByIdDesc(any(), any()));
    }

    @Test
    void deleteTransactionsByConnectionId() {
        service.deleteTransactionsByConnectionId(1L);
        verify(dao).deleteTransactionsByConnection_Id(1L);
    }
}
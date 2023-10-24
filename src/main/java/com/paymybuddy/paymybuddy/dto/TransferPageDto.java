package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferPageDto {
    private User userConnected;
    private List<Connection> connections;
    private Page<Transaction> transactions;
    private List<Integer> pageNumbers;
    private Integer currentPage = 1;
    private Integer totalPage = 1;
}

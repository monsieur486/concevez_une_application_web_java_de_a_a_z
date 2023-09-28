package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
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

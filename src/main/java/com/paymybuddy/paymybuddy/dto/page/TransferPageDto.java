package com.paymybuddy.paymybuddy.dto.page;

import com.paymybuddy.paymybuddy.dto.form.TransactionFormDto;
import com.paymybuddy.paymybuddy.entity.Connection;
import com.paymybuddy.paymybuddy.entity.Transaction;
import com.paymybuddy.paymybuddy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferPageDto {
    private String solde;
    private TransactionFormDto transactionForm;
    private Page<Transaction> transactions;
    private List<Integer> pageNumbers;
    private Integer currentPage = 1;
    private Integer totalPage = 1;
}

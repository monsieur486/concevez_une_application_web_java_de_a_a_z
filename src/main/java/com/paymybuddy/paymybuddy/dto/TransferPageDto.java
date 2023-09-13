package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferPageDto {
    private String email;
    private String amount;
    private List<Transaction> transactions;
}

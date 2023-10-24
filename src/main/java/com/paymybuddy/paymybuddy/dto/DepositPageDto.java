package com.paymybuddy.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositPageDto {
    private String email;
    private String solde;
    private Integer amount;
}

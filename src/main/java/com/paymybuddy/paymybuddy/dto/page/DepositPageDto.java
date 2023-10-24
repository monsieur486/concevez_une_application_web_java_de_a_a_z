package com.paymybuddy.paymybuddy.dto.page;

import com.paymybuddy.paymybuddy.dto.form.DepositFormDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositPageDto {
    private String solde;
    private DepositFormDto depositForm;
}

package com.paymybuddy.paymybuddy.dto.page;

import com.paymybuddy.paymybuddy.dto.form.WithdrawalFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawalPageDto {
    private String solde;
    private WithdrawalFormDto withdrawalForm;
}

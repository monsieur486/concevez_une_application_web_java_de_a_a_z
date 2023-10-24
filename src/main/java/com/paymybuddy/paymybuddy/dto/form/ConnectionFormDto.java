package com.paymybuddy.paymybuddy.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionFormDto {
    private String email;
    private String nickname;
}

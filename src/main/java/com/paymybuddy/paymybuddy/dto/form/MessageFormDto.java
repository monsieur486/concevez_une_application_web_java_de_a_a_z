package com.paymybuddy.paymybuddy.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageFormDto {
    private String email;
    private String content;
}

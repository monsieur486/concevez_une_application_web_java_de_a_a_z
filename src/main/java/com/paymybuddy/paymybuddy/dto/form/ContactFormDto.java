package com.paymybuddy.paymybuddy.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactFormDto {
    private String email = "";
    private String content = "";
}

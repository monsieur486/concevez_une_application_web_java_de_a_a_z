package com.paymybuddy.paymybuddy.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFormDto {
    private Long id;
    private String email;
    private String password;
    private String passwordForVerification;

    public Boolean isPasswordMatching() {
        return password.equals(passwordForVerification);
    }
}

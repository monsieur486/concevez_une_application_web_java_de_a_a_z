package com.paymybuddy.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String passwordForVerification;

    public Boolean isPasswordMatching() {
        return password.equals(passwordForVerification);
    }
}

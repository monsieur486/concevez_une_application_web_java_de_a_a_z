package com.paymybuddy.paymybuddy.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @Email
    private String email;
    private String password;
    private String passwordForVerification;

    public Boolean isPasswordMatching() {
        return password.equals(passwordForVerification);
    }
}

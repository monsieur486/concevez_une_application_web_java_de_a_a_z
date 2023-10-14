package com.paymybuddy.paymybuddy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String connectionEmail;

    @NotEmpty(message = "Nickname should not be empty")
    private String nickname;

}

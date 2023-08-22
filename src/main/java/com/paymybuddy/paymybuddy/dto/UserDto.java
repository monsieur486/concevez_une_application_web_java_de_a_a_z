package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.config.ApplicationConfiguration;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = ApplicationConfiguration.MINIMUM_PASSWORD_LENGTH,
            max = ApplicationConfiguration.MAXIMUM_PASSWORD_LENGTH,
            message = "Password should have at least 8 characters and at most 50 characters")
    private String password;
}

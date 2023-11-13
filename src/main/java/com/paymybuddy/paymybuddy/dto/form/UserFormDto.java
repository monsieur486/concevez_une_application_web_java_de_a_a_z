package com.paymybuddy.paymybuddy.dto.form;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFormDto {
    private Long id = 0L;
    private String email = "";
    private String password = "";
    private String passwordForVerification = "";

    public Boolean isPasswordMatching() {
        return password.equals(passwordForVerification);
    }

    public String toJson() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        return gson.toJson(this);
    }
}

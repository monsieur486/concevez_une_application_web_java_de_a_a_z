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
public class WithdrawalFormDto {
    private Double amount = 0.0;

    public String toJson() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        return gson.toJson(this);
    }
}

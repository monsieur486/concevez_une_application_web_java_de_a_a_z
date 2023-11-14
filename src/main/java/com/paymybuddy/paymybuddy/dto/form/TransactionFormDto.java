package com.paymybuddy.paymybuddy.dto.form;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paymybuddy.paymybuddy.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TransactionFormDto {
    List<Connection> connections;
    Long connectionId = 0L;
    Integer amount = 0;
    String description = "";

    public TransactionFormDto() {
        this.connections = List.of();
    }

    public String toJson() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        return gson.toJson(this);
    }
}

package com.paymybuddy.paymybuddy.dto.form;

import com.paymybuddy.paymybuddy.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionFormDto {
    List<Connection> connections;
    Long connectionId = 0L;
    Integer amount;
    String description;

    public TransactionFormDto() {
        this.connections = List.of();
    }
}

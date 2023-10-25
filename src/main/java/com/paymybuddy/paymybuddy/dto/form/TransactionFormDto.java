package com.paymybuddy.paymybuddy.dto.form;

import com.paymybuddy.paymybuddy.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFormDto {
    List<Connection> connections;
    Integer amount;
    String description;

    public TransactionFormDto(List<Connection> connections) {
        this.connections = connections;
    }
}

package com.paymybuddy.paymybuddy.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Connection connection;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer amount;
}

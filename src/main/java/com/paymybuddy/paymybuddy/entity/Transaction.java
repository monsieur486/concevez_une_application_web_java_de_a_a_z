package com.paymybuddy.paymybuddy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Connection connection;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}

package com.paymybuddy.paymybuddy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {
    @Id
    @Column(name = "series", nullable = false, length = 64)
    private String series;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "token", nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Timestamp lastUsed;

}

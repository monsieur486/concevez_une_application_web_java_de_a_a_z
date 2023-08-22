package com.paymybuddy.paymybuddy.utils;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="persistent_logins")
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

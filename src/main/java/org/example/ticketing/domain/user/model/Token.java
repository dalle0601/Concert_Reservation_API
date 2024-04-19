package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="TOKEN")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long tokenId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TOKEN_VALUE")
    private String tokenValue;

    @Column(name = "EXPIRED_AT")
    private LocalDateTime expiredAt;

    @Column(name = "USE")
    private boolean use;

    public Token() {}

    @Builder
    public Token( Long userId, String token_value, LocalDateTime expiredAt, boolean use) {
        this.userId = userId;
        this.tokenValue = token_value;
        this.expiredAt = expiredAt;
        this.use = use;
    }
    public Token(Long token_id, Long userId, String token_value, LocalDateTime expiredAt, boolean use) {
        this.tokenId = token_id;
        this.userId = userId;
        this.tokenValue = token_value;
        this.expiredAt = expiredAt;
        this.use = use;
    }
}


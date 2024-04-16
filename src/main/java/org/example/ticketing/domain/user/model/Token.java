package org.example.ticketing.domain.user.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

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

    public Token() {}

    @Builder
    public Token( Long user_id, String token_value, LocalDateTime expiredAt) {
        this.userId = user_id;
        this.tokenValue = token_value;
        this.expiredAt = expiredAt;
    }
    public Token(Long token_id, Long user_id, String token_value, LocalDateTime expiredAt) {
        this.tokenId = token_id;
        this.userId = user_id;
        this.tokenValue = token_value;
        this.expiredAt = expiredAt;
    }
}


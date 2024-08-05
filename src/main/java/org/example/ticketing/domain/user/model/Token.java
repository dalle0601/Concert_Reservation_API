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
    private String userId;

    @Column(name = "TOKEN_VALUE")
    private String tokenValue;

    @Column(name = "EXPIRED_AT")
    private LocalDateTime expiredAt;

    @Column(name = "USE_STATE")
    private boolean useState;

    public Token() {}

    @Builder
    public Token( String userId, String token_value, LocalDateTime expiredAt, boolean useState) {
        this.userId = userId;
        this.tokenValue = token_value;
        this.expiredAt = expiredAt;
        this.useState = useState;
    }
    public Token(Long token_id, String userId, String token_value, LocalDateTime expiredAt, boolean useState) {
        this.tokenId = token_id;
        this.userId = userId;
        this.tokenValue = token_value;
        this.expiredAt = expiredAt;
        this.useState = useState;
    }
}


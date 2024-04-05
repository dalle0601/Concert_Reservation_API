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

    @Column(name = "UDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public Token() {}

    @Builder
    public Token( Long user_id, String token_value, LocalDateTime updated_at, LocalDateTime created_at) {
        this.userId = user_id;
        this.tokenValue = token_value;
        this.updatedAt = updated_at;
        this.createdAt = created_at;
    }
    public Token(Long token_id, Long user_id, String token_value, LocalDateTime updated_at, LocalDateTime created_at) {
        this.tokenId = token_id;
        this.userId = user_id;
        this.tokenValue = token_value;
        this.updatedAt = updated_at;
        this.createdAt = created_at;
    }
}


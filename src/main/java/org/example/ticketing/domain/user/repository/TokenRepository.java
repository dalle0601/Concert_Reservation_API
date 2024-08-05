package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Token;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenRepository {
    Token findByUserId(Long userId);
    Long findTokenCount();
    Token enterToken(Token tokenValue);
    List<Token> findByExpiredAtBefore(LocalDateTime currentTime);
    void deleteToken(List<Token> tokens);
    void deleteByUserIdAndUseTrue(String tokenValue, boolean useState);
}

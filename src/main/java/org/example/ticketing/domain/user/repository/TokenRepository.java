package org.example.ticketing.domain.user.repository;

import org.example.ticketing.api.dto.request.TokenRequestDTO;
import org.example.ticketing.domain.user.model.Token;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenRepository {
    Token findByUserId(Long user_id);
    Long findTokenCount();
    Token enterToken(Token tokenValue);
    List<Token> findByExpiredAtBefore(LocalDateTime currentTime);
    void deleteAll(List<Token> tokens);

}

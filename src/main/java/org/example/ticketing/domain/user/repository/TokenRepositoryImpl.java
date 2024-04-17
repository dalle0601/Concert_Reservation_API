package org.example.ticketing.domain.user.repository;

import org.example.ticketing.api.dto.request.TokenRequestDTO;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepository{
    private final TokenJpaRepository tokenJpaRepository;
    public TokenRepositoryImpl(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Token findByUserId(Long user_id) {
        Optional<Token> tokenOptional = tokenJpaRepository.findByUserId(user_id);
        return tokenOptional.orElse(null);
    }

    @Override
    public Long findTokenCount() {
        return tokenJpaRepository.findTokenCount();
    }

    @Override
    public Token enterToken(Token tokenValue) {
        return tokenJpaRepository.save(tokenValue);
    }

    @Override
    public List<Token> findByExpiredAtBefore(LocalDateTime currentTime) {
        return tokenJpaRepository.findByExpiredAtBefore(currentTime);
    }

    @Override
    public void deleteToken(List<Token> tokens) {
        tokenJpaRepository.deleteToken(tokens);
    }
}

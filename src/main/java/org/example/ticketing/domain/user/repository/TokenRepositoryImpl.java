package org.example.ticketing.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.user.model.Token;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository{
    private final TokenJpaRepository tokenJpaRepository;
    @Override
    public Token findByUserId(Long userId) {
        Optional<Token> tokenOptional = tokenJpaRepository.findByUserId(userId);
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

    @Override
    public void deleteByUserIdAndUseTrue(String tokenValue, boolean use) {
        tokenJpaRepository.updateUseByTokenValue(tokenValue, use);
    }
}

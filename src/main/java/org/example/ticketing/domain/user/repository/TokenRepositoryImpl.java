package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepository{
    private final TokenJpaRepository tokenJpaRepository;
    public TokenRepositoryImpl(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Token tokenInsertOrUpdate(Long user_id, String token) {
        Optional<Token> existingTokenOptional = tokenJpaRepository.findByUserId(user_id);

        if (existingTokenOptional.isPresent()) {
            Token existingToken = existingTokenOptional.get();
            existingToken = new Token(existingToken.getTokenId(), existingToken.getUserId(), token, LocalDateTime.now(), existingToken.getCreatedAt());
            return tokenJpaRepository.save(existingToken);
        } else {
            LocalDateTime nowDate = LocalDateTime.now();
            Token newToken = new Token(user_id, token, nowDate, nowDate);
            return tokenJpaRepository.save(newToken); // 새로운 엔티티 저장
        }
    }

    @Override
    public Token findByUserId(Long user_id) {
        Optional<Token> tokenOptional = tokenJpaRepository.findByUserId(user_id);
        return tokenOptional.orElse(null);
    }
}

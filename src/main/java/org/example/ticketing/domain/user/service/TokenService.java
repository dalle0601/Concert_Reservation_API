package org.example.ticketing.domain.user.service;

import jakarta.transaction.Transactional;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    public TokenService (TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    public Token checkToken(UserRequestDTO userRequestDTO) {
        return tokenRepository.findByUserId(userRequestDTO.userId());
    }
    public Long findTokenCount() {
        return tokenRepository.findTokenCount();
    }
    @Transactional
    public void enterToken(Token tokenValue) {
        tokenRepository.enterToken(tokenValue);
    }

    public List<Token> findByExpiredAtBefore(LocalDateTime currentTime){
        return tokenRepository.findByExpiredAtBefore(currentTime);
    }
    @Transactional
    public void deleteExpiredTokens(List<Token> expiredTokens){
        tokenRepository.deleteToken(expiredTokens);
    }

    public void deleteByUserIdAndUseTrue(String tokenValue, boolean use) {
        tokenRepository.deleteByUserIdAndUseTrue(tokenValue, use);
    }
}

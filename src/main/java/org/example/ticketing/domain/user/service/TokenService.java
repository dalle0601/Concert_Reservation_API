package org.example.ticketing.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    public Token checkToken(UserRequestDTO userRequestDTO) {
        return tokenRepository.findByUserId(userRequestDTO.userId());
    }
    public Long findTokenCount() {
        return tokenRepository.findTokenCount();
    }
    public void enterToken(Token tokenValue) {
        tokenRepository.enterToken(tokenValue);
    }
    public List<Token> findByExpiredAtBefore(LocalDateTime currentTime){
        return tokenRepository.findByExpiredAtBefore(currentTime);
    }
    public void deleteExpiredTokens(List<Token> expiredTokens){
        tokenRepository.deleteToken(expiredTokens);
    }
    public void deleteByUserIdAndUseTrue(String tokenValue, boolean useState) {
        tokenRepository.deleteByUserIdAndUseTrue(tokenValue, useState);
    }
}

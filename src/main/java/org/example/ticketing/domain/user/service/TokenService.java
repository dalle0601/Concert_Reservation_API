package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;
    public TokenService (TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token checkToken(UserRequestDTO userRequestDTO) {
        return tokenRepository.findByUserId(userRequestDTO.user_id());
    }

    public Long findTokenCount() {
        return tokenRepository.findTokenCount();
    }

    public Token enterToken(Token tokenValue) {
        return tokenRepository.enterToken(tokenValue);
    }

}

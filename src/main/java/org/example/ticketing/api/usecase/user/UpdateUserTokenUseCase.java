package org.example.ticketing.api.usecase;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.common.TokenQueueTableUpdate;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserTokenUseCase {
    private final TokenRepository tokenRepository;
    private final TokenQueueTableUpdate tokenQueueTableUpdate;

    public UpdateUserTokenUseCase(TokenRepository tokenRepository, TokenQueueTableUpdate tokenQueueTableUpdate) {
        this.tokenRepository = tokenRepository;
        this.tokenQueueTableUpdate = tokenQueueTableUpdate;
    }

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        Token getToken = tokenRepository.findByUserId(userRequestDTO.user_id());
        return tokenQueueTableUpdate.execute(userRequestDTO, getToken.getTokenValue());
    }
}

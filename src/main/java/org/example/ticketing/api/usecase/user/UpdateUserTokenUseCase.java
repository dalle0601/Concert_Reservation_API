package org.example.ticketing.api.usecase.user;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.common.UpdateTokenQueueWaitInfo;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserTokenUseCase {
    private final TokenRepository tokenRepository;
    private final UpdateTokenQueueWaitInfo updateTokenQueueWaitInfo;

    public UpdateUserTokenUseCase(TokenRepository tokenRepository, UpdateTokenQueueWaitInfo updateTokenQueueWaitInfo) {
        this.tokenRepository = tokenRepository;
        this.updateTokenQueueWaitInfo = updateTokenQueueWaitInfo;
    }

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        Token getToken = tokenRepository.findByUserId(userRequestDTO.user_id());
        return updateTokenQueueWaitInfo.execute(userRequestDTO, getToken.getTokenValue());
    }
}

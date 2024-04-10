package org.example.ticketing.api.usecase;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfirmUserTokenUseCase {
    /*
        토큰 대기열 상태 확인 요청
        - token Table 조회
     */
    private final TokenRepository tokenRepository;

    public ConfirmUserTokenUseCase(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        Token token = tokenRepository.findByUserId(userRequestDTO.user_id());
        return new TokenResponseDTO(token.getTokenValue());
    }
}


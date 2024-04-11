package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.ConfirmUserTokenUseCase;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final ConfirmUserTokenUseCase confirmUserTokenUseCase;

    public TokenService (ConfirmUserTokenUseCase confirmUserTokenUseCase) {
        this.confirmUserTokenUseCase = confirmUserTokenUseCase;
    }
    public TokenResponseDTO checkToken(UserRequestDTO userRequestDTO) {
        return confirmUserTokenUseCase.execute(userRequestDTO);
    }
}

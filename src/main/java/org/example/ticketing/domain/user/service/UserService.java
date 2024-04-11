package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.IssueUserTokenUseCase;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IssueUserTokenUseCase issueUserTokenUseCase;

    public UserService (IssueUserTokenUseCase issueUserTokenUseCase) {
        this.issueUserTokenUseCase = issueUserTokenUseCase;
    }

    public TokenResponseDTO issueToken(UserRequestDTO userRequestDTO) {
        return issueUserTokenUseCase.execute(userRequestDTO);
    }

}

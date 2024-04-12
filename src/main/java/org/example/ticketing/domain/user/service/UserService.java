package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.api.usecase.user.IssueUserTokenUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IssueUserTokenUseCase issueUserTokenUseCase;

    private final GetPointUseCase getPointUseCase;
    public UserService (IssueUserTokenUseCase issueUserTokenUseCase, GetPointUseCase getPointUseCase) {
        this.issueUserTokenUseCase = issueUserTokenUseCase;
        this.getPointUseCase = getPointUseCase;
    }
    public TokenResponseDTO issueToken(UserRequestDTO userRequestDTO) {
        return issueUserTokenUseCase.execute(userRequestDTO);
    }
    public UserResponseDTO getPoint(Long user_id){
        return getPointUseCase.execute(user_id);
    }
}

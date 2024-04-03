package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.usecase.IssueUserTokenUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IssueUserTokenUseCase issueUserTokenUseCase;

    public UserService (IssueUserTokenUseCase issueUserTokenUseCase) {
        this.issueUserTokenUseCase = issueUserTokenUseCase;
    }

    public String issueToken(UserRequestDTO userRequestDTO) {
        return issueUserTokenUseCase.execute(userRequestDTO);
    }

}

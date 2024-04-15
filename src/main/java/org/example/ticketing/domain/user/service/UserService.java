package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.api.usecase.user.IssueUserTokenUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GetPointUseCase getPointUseCase;
    public UserService (UserRepository userRepository, GetPointUseCase getPointUseCase) {
        this.userRepository = userRepository;
        this.getPointUseCase = getPointUseCase;
    }
    public UserInfo findUserInfo(UserRequestDTO userRequestDTO) {
        return userRepository.findUserByUserId(userRequestDTO.user_id());
    }

    public UserInfo joinUser(UserRequestDTO userRequestDTO){
        return userRepository.joinUser(userRequestDTO.user_id());
    }

    public UserInfo getPoint(Long user_id){
        return getPointUseCase.execute(user_id);
    }
}

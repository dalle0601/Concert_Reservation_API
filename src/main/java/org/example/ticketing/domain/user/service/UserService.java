package org.example.ticketing.domain.user.service;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserInfo findUserInfo(UserRequestDTO userRequestDTO) {
        return userRepository.findUserByUserId(userRequestDTO.user_id());
    }

    public UserInfo joinUser(UserRequestDTO userRequestDTO){
        return userRepository.joinUser(userRequestDTO.user_id());
    }
}

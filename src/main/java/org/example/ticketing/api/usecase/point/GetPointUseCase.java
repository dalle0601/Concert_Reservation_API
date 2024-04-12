package org.example.ticketing.api.usecase.point;

import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPointUseCase {
    private final UserRepository userRepository;

    public GetPointUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO execute(Long user_id){
        return userRepository.findUserByUserId(user_id);
    }
}

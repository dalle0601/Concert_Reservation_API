package org.example.ticketing.domain.user.service;

import jakarta.transaction.Transactional;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
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
        return userRepository.findUserByUserId(userRequestDTO.userId());
    }
    @Transactional
    public UserInfo joinUser(UserRequestDTO userRequestDTO){
        return userRepository.joinUser(userRequestDTO.userId());
    }
    @Transactional
    public UserInfo chargePoint(UserInfo userInfo){
        return userRepository.save(userInfo);
    }

    public void paymentPoint(Long userId, Long point) {
        userRepository.updatePointByUserId(userId, point);
    }
}

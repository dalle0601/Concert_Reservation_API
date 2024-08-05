package org.example.ticketing.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserInfo findUserInfo(UserRequestDTO userRequestDTO) {
        return userRepository.findUserByUserId(userRequestDTO.userId());
    }
    public UserInfo joinUser(UserRequestDTO userRequestDTO){
        return userRepository.joinUser(userRequestDTO.userId());
    }
    public UserInfo chargePoint(UserInfo userInfo){
        return userRepository.save(userInfo);
    }
    public void paymentPoint(String userId, Long point) {
        userRepository.updatePointByUserId(userId, point);
    }
    public boolean existByUserId(String userId) { return userRepository.existsByUserId(userId);}
    public void save(UserInfo userInfo) { userRepository.save(userInfo); }
}

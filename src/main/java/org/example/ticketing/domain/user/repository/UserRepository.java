package org.example.ticketing.domain.user.repository;

import org.example.ticketing.api.dto.response.QueueWaitInfoResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.domain.user.model.UserInfo;

public interface UserRepository {
    // 유저 등록
    UserInfo joinUser(Long userId);
    UserInfo findUserByUserId(Long userId);
}

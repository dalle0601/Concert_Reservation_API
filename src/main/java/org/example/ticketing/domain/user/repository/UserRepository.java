package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.UserInfo;

public interface UserRepository {
    // 유저 등록
    UserInfo joinUser(Long userId);
    UserInfo findUserByUserId(Long userId);
    UserInfo save(UserInfo userInfo);
    void updatePointByUserId(Long userId, Long point);
}

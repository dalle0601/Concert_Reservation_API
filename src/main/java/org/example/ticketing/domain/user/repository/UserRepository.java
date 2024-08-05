package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.UserInfo;

public interface UserRepository {
    // 유저 등록
    UserInfo joinUser(String userId);
    UserInfo findUserByUserId(String userId);
    UserInfo save(UserInfo userInfo);
    void updatePointByUserId(String userId, Long point);
    boolean existsByUserId(String usreId);
}

package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.UserInfo;

public interface UserRepository {
    // 유저 등록
    UserInfo joinUser(String uuid, String userId);
}

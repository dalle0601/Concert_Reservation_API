package org.example.ticketing.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserInfo joinUser(Long userId) {
        return userJpaRepository.save(UserInfo.builder()
                .userId(userId).build());
    }
    @Override
    public UserInfo findUserByUserId(Long userId) {
        Optional<UserInfo> userOptional = userJpaRepository.findByUserId(userId);
        return userOptional.orElse(null);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userJpaRepository.save(userInfo);
    }

    @Override
    public void updatePointByUserId(Long userId, Long point) {
        userJpaRepository.updatePointByUserId(userId, point);
    }
}

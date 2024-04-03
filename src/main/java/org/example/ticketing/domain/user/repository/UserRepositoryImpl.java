package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserInfo joinUser(String uuid, String userId) {
        return userJpaRepository.save(UserInfo.builder()
                .uuid(uuid)
                .userId(userId).build());
    }
}

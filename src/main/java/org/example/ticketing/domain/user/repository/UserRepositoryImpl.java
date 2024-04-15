package org.example.ticketing.domain.user.repository;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.example.ticketing.api.dto.response.QueueWaitInfoResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserInfo joinUser(Long user_id) {
        return userJpaRepository.save(UserInfo.builder()
                .userId(user_id).build());
    }
    @Override
    public UserInfo findUserByUserId(Long user_id) {
        Optional<UserInfo> userOptional = userJpaRepository.findByUserId(user_id);
        return userOptional.orElse(null);
    }
}

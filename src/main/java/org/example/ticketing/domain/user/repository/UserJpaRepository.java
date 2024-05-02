package org.example.ticketing.domain.user.repository;

import jakarta.transaction.Transactional;
import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE UserInfo u SET u.point = :point WHERE u.userId = :userId")
    void updatePointByUserId(@Param("userId") Long userId, @Param("point") Long point);
}

package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserInfo, Long> {
//    User save(String uuid, String userId);
}

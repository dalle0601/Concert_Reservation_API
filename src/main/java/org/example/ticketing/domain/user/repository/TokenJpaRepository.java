package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.userId = :userId")
    Optional<Token> findByUserId(@Param("userId") Long userId);
}

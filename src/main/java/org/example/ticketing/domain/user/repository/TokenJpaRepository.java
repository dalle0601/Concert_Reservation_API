package org.example.ticketing.domain.user.repository;

import jakarta.transaction.Transactional;
import org.example.ticketing.domain.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND t.use = true")
    Optional<Token> findByUserId(@Param("userId") Long userId);
    @Query("SELECT COUNT(*) as tokenCount FROM Token WHERE use = true")
    Long findTokenCount();
    @Query("SELECT t FROM Token t WHERE t.expiredAt < :currentTime AND t.use = true")
    List<Token> findByExpiredAtBefore(@Param("currentTime") LocalDateTime currentTime);
    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.use = false WHERE t IN :tokens")
    void deleteToken(List<Token> tokens);
}

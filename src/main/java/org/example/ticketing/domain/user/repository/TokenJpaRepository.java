package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND t.useState = true")
    Optional<Token> findByUserId(@Param("userId") String userId);
    @Query("SELECT COUNT(*) as tokenCount FROM Token WHERE useState = true")
    Long findTokenCount();
    @Query("SELECT t FROM Token t WHERE t.expiredAt < :currentTime AND t.useState = true")
    List<Token> findByExpiredAtBefore(@Param("currentTime") LocalDateTime currentTime);

    @Modifying
    @Query("UPDATE Token t SET t.useState = false WHERE t IN :tokens")
    void deleteToken(List<Token> tokens);

    @Modifying
    @Query("UPDATE Token t SET t.useState = :useState WHERE t.tokenValue = :tokenValue")
    void updateUseByTokenValue(String tokenValue, boolean useState);
}

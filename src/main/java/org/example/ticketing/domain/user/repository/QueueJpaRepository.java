package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    @Query("SELECT q FROM Queue q WHERE q.userId = :userId")
    Optional<Queue> findByUserId(@Param("userId") Long userId);
    @Query("SELECT COUNT(q) FROM Queue q WHERE q.updatedAt < :myTime")
    Long findQueueCount(@Param("myTime") LocalDateTime myTime);
    @Query("SELECT q FROM Queue q ORDER BY q.updatedAt ASC limit 10")
    List<Queue> findFirstNOrderByUpdatedAtAsc(int count);
}

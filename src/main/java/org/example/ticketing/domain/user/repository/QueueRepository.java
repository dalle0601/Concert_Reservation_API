package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Queue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    Optional<Queue> findByUserId(String userId);
    Queue enterQueue(String userId);
    Long findQueueCount(LocalDateTime myTime);
    void deleteQueue(String userId) throws Exception;
    List<Queue> findFirstNOrderByUpdatedAtAsc(int count);
}

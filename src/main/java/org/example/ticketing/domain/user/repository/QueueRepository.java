package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Queue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    Object getQueueOngoingAndWaitInfo();
    Optional<Queue> findByUserId(Long user_id);
    Queue queueInsertOrUpdate(Long user_id, String status);
    void updateQueueStatus(Long user_id, String status);
}

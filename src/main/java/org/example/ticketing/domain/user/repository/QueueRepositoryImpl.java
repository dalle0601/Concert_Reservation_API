package org.example.ticketing.domain.user.repository;

import org.example.ticketing.domain.user.model.Queue;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class QueueRepositoryImpl implements QueueRepository{
    private final QueueJpaRepository queueJpaRepository;
    public QueueRepositoryImpl(QueueJpaRepository queueJpaRepository) {
        this.queueJpaRepository = queueJpaRepository;
    }

    @Override
    public Object getQueueOngoingAndWaitInfo() {
        return queueJpaRepository.getQueueOngoingAndWait();
    }

    @Override
    public Optional<Queue> findByUserId(Long user_id) {
        return queueJpaRepository.findByUserId(user_id);
    }

    @Override
    public Queue queueInsertOrUpdate(Long user_id, String status) {
        Optional<Queue> existingQueueOptional = queueJpaRepository.findByUserId(user_id);

        if (existingQueueOptional.isPresent()) {
            Queue existingQueue = existingQueueOptional.get();
            existingQueue = new Queue(existingQueue.getQueueId(), user_id, status, LocalDateTime.now(), existingQueue.getCreatedAt());
            return queueJpaRepository.save(existingQueue);
        } else {
            LocalDateTime nowDate = LocalDateTime.now();
            Queue newQueue = new Queue(user_id, status, nowDate, nowDate);
            return queueJpaRepository.save(newQueue); // 새로운 엔티티 저장
        }
    }
}

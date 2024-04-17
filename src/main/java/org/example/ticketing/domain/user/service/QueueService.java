package org.example.ticketing.domain.user.service;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.example.ticketing.domain.user.model.Queue;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {
    private final QueueRepository queueRepository;

    public QueueService (QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public Queue findQueueInfo(Long userId) {
        return queueRepository.findByUserId(userId).orElse(null);
    }
    @Transactional
    public Queue enterQueue(Long userId) {
        return queueRepository.enterQueue(userId);
    }
    public Long findQueueCount(LocalDateTime myTime) {
        return queueRepository.findQueueCount(myTime);
    }
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteQueue(Long userId) throws Exception {
        queueRepository.deleteQueue(userId);
    }
    public List<Queue> getUsersToRemove(int count) {
        return queueRepository.findFirstNOrderByUpdatedAtAsc(count);
    }
}

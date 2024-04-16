package org.example.ticketing.domain.user.service;

import jakarta.transaction.Transactional;
import org.example.ticketing.api.dto.response.QueueWaitInfoResponseDTO;
import org.example.ticketing.domain.user.model.Queue;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.springframework.stereotype.Service;

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
    public Long findQueueCount() {
        return queueRepository.findQueueCount();
    }
    @Transactional
    public void deleteQueue(Long userId) throws Exception {
        queueRepository.deleteQueue(userId);
    }
}

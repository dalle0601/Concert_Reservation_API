package org.example.ticketing.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.domain.user.model.Queue;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final QueueRepository queueRepository;

    public Queue findQueueInfo(Long userId) {
        return queueRepository.findByUserId(userId).orElse(null);
    }
    public Queue enterQueue(Long userId) {
        return queueRepository.enterQueue(userId);
    }
    public Long findQueueCount(LocalDateTime myTime) {
        return queueRepository.findQueueCount(myTime);
    }
    public void deleteQueue(Long userId) throws Exception {
        queueRepository.deleteQueue(userId);
    }
    public List<Queue> getUsersToRemove(int count) {
        return queueRepository.findFirstNOrderByUpdatedAtAsc(count);
    }
}

package org.example.ticketing.domain.user.service;

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

    public QueueWaitInfoResponseDTO findQueueOngoingAndWaitInfo() {
        return queueRepository.getQueueOngoingAndWaitInfo();
    }
}

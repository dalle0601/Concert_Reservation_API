package org.example.ticketing.domain.user.service;

import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueSchedulerService {
    private TokenRepository tokenRepository;
    public QueueSchedulerService (TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    @Scheduled(fixedRate = 10000) // milliseconds
    public void deleteExpiredRows() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Token> expiredRows = tokenRepository.findByExpiredAtBefore(currentTime);
        tokenRepository.deleteAll(expiredRows);
    }
}

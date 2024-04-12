package org.example.ticketing.api.usecase.common;

import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTokenQueueStatus {
    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;

    public UpdateTokenQueueStatus(QueueRepository queueRepository, TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    public void execute(Long user_id, String token_status) {
        queueRepository.queueInsertOrUpdate(user_id, token_status);
        tokenRepository.tokenInsertOrUpdate(user_id, token_status);
    }

}

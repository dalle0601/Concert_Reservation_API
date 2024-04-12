package org.example.ticketing.api.usecase.common;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTokenQueueWaitInfo {
    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;

    public UpdateTokenQueueWaitInfo(QueueRepository queueRepository, TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO, String token) {
        // 내 대기열 정보를 Queue 테이블에 update or insert
        tokenInsertOrUpdateQueue(userRequestDTO.user_id(), token.split("/")[1]);
        // Token table 에 user_id, token 정보<uuid + / + status> insert or update
        tokenRepository.tokenInsertOrUpdate(userRequestDTO.user_id(), token);

        return new TokenResponseDTO(token);
    }

    /*
        대기열 정보 까지 포함된 토큰 발급 후
        Queue Table에 내 대기열 Insert OR Update
     */
    private void tokenInsertOrUpdateQueue(Long user_id, String status) {
        queueRepository.queueInsertOrUpdate(user_id, status);
    }
}

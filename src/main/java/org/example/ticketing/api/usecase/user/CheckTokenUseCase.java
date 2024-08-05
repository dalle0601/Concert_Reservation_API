package org.example.ticketing.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckTokenUseCase {
    private final TokenManager tokenManager;
    private final QueueManager queueManager; // QueueManager 주입

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        String userId = userRequestDTO.userId();
        Map<String, String> token = tokenManager.getCheckTokenInfo(userId);
        if (token.get("token") != null) {
            return new TokenResponseDTO("유효한 토큰입니다.", token.get("token"), token.get("expirationTime"), null);
        } else {
            // 대기열에서 사용자의 위치를 계산
            int queuePosition = queueManager.getQueuePosition(userId);
            return new TokenResponseDTO("유효하지 않은 토큰입니다.", null, null, queuePosition);
        }
    }
}

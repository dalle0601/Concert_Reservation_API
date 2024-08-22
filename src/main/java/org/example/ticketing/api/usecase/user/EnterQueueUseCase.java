package org.example.ticketing.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EnterQueueUseCase {
    private final TokenManager tokenManager;
    private final QueueManager queueManager;

    public QueueResponseDTO execute(UserRequestDTO userRequestDTO) throws Exception {
        long validTokenCount = tokenManager.getValidTokenCount();

        String userId = userRequestDTO.userId();
        if (validTokenCount < 1) {
            Map<String, String> checkToken = tokenManager.getCheckTokenInfo(userId);
            if (checkToken.get("token") == null) {
                Map<String, String> tokenValue = tokenManager.issueToken(userId);
                return new QueueResponseDTO("유효토큰이 발급되었습니다.", 0L, tokenValue.get("expirationTime"));
            } else {
                // 시스템 기본 타임존을 사용하여 LocalDateTime으로 변환
                return new QueueResponseDTO("이미 유효토큰이 발급되었습니다.", 0L, checkToken.get("expirationTime"));
            }
        } else {
            try {
                // 사용자 추가 전에 대기열에 있는지 확인
                queueManager.addToQueue(userRequestDTO.userId());
                long waitInfo = queueManager.getQueuePosition(userRequestDTO.userId());
                return new QueueResponseDTO("대기열에 포함되었습니다.", waitInfo, null);
            } catch (Exception e) {
                return new QueueResponseDTO("대기열 진입 과정에서 에러가 발생했습니다.", null, null);
            }
        }
    }
}

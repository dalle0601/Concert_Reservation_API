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

        long userId = userRequestDTO.userId();
        if (validTokenCount < 3) {
            Map<String, String> checkToken = tokenManager.getCheckTokenInfo(userId);
            if (checkToken.get("token") == null) {
                Map<String, String> tokenValue = tokenManager.issueToken(userId);
                return new QueueResponseDTO("유효토큰이 발급되었습니다.", 0L, tokenValue.get("expirationTime"));
            } else {
                // 시스템 기본 타임존을 사용하여 LocalDateTime으로 변환
                return new QueueResponseDTO("이미 유효토큰이 발급되었습니다.", 0L, checkToken.get("expirationTime"));
            }
        } else {
            queueManager.addToQueue(userRequestDTO.userId());
            long waitInfo = queueManager.getQueuePosition(userRequestDTO.userId());
            if(waitInfo == -1){
                return new QueueResponseDTO("대기중이지 않습니다.", waitInfo, null);
            } else {
                return new QueueResponseDTO("대기정보 조회 성공", waitInfo, null);
            }


        }
    }
}

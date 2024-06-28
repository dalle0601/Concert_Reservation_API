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
public class UpdateTokenUseCase {
    private final TokenManager tokenManager;
    private final QueueManager queueManager;

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        Long firstUserInQueue = queueManager.getNextInQueue();
        long userId = userRequestDTO.userId();
        if(firstUserInQueue != null) {
            if (userId == firstUserInQueue) {
                long validTokenCount = tokenManager.getValidTokenCount();
                if (validTokenCount < 3) {
                    // 유효 토큰 발급
                    Map<String, String> tokenValue = tokenManager.issueToken(userId);
                    queueManager.removeUserFromQueue(userId); // 대기열에서 사용자 삭제
                    return new TokenResponseDTO("유효토큰이 발급되었습니다.", tokenValue.get("token"), tokenValue.get("expirationTime"), null);
                } else {
                    // 대기
                    return new TokenResponseDTO("이제 곧 입장합니다.", "0", null, null);
                }
            } else {
                // 대기 번호 리턴
                long waitCount = queueManager.getQueuePosition(userId);
                if(waitCount == -1) {
                    return new TokenResponseDTO("대기열에 사용자가 없습니다.", "-1", null, null);
                }
                return new TokenResponseDTO("대기상태입니다.", String.valueOf(waitCount), null, null);
            }
        } else {
            return new TokenResponseDTO("대기열에 사용자가 없습니다.", "-1", null, null);
        }
    }
}

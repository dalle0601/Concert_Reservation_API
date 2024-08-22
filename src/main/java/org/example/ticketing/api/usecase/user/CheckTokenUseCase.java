package org.example.ticketing.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
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
    private final EnterQueueUseCase enterQueueUseCase;
    private final UpdateTokenUseCase updateTokenUseCase;

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        String userId = userRequestDTO.userId();

        Map<String, String> token = tokenManager.getCheckTokenInfo(userId);

        // 1. 유효토큰 조회

        // 1-1. 있다? -> 유효한 토큰이라고 넘김
        if (token.get("token") != null) {
            return new TokenResponseDTO("유효한 토큰입니다.", token.get("token"), token.get("expirationTime"), null);
        }
        // 1-2. 없다? -> 대기열 조회
        else {
            int queuePosition = queueManager.getQueuePosition(userId);
            // 1-3. 대기열 조회했는데 없다? -> 그냥 다시 대기열 포함시키기
            if(queuePosition == -1) {
                try {
                    QueueResponseDTO queueResponseDTO = enterQueueUseCase.execute(new UserRequestDTO(userId));
                    return new TokenResponseDTO(queueResponseDTO.message(), null, null, queueResponseDTO.waitCount().intValue());
                } catch (Exception e) {
                    return new TokenResponseDTO("대기열 진입 과정에서 에러가 발생했습니다.", null, null, null);
                }
            }
            // 1-4. 대기열 조회했는데 있다? -> 대기열 정보 리턴해주기
            // 대기열에서 사용자의 위치를 계산
            else {
                return updateTokenUseCase.execute(new UserRequestDTO(userId));
//                return new TokenResponseDTO("유효하지 않은 토큰입니다.", null, null, queuePosition);
            }

        }
    }
}

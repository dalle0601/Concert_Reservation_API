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
//    private final TokenService tokenService;
//    private final UpdateQueueUseCase updateQueueUseCase;
//
//    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) throws Exception {
//        Token token = tokenService.checkToken(userRequestDTO);
//        if(token == null) {
//            QueueResponseDTO queueResponseDTO = updateQueueUseCase.execute(userRequestDTO);
//            return new TokenResponseDTO("토큰이 확인되지 않습니다.", null, null);
//        } else {
//            return new TokenResponseDTO("유효한 토큰입니다.", token.getTokenValue(), token.getExpiredAt());
//        }
//
//    }
    private final TokenManager tokenManager;
    private final QueueManager queueManager;

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        long firstUserInQueue = Long.parseLong(queueManager.getNextInQueue());
        long userId = userRequestDTO.userId();

        if (userId == firstUserInQueue ) {
            long validTokenCount = tokenManager.getValidTokenCount();
            if (validTokenCount < 3) {
                // 유효 토큰 발급
                Map<String, String> tokenValue = tokenManager.issueToken(userId);
                return new TokenResponseDTO("유효토큰이 발급되었습니다.", tokenValue.get("expirationTime"), null);
            } else {
                // 대기
                return new TokenResponseDTO("이제 곧 입장합니다.","0", null);
            }
        } else {
            // 대기 번호 리턴
            return new TokenResponseDTO("대기상태입니다.", String.valueOf(queueManager.getQueuePosition(userId)), null);
//                    "현재 대기 번호: " + queueManager.getQueuePosition(userId);
        }
    }
}

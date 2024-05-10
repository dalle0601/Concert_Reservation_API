package org.example.ticketing.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EnterQueueUseCase {
    private final TokenManager tokenManager;
    private final QueueManager queueManager;

    public QueueResponseDTO execute(UserRequestDTO userRequestDTO) throws Exception {
        long validTokenCount = tokenManager.getValidTokenCount();

        if(validTokenCount < 3) {
            Map<String, String> tokenValue = tokenManager.issueToken(userRequestDTO.userId());

            LocalDateTime expirationTime = timeFormatter(tokenValue.get("expirationTime"));
            return new QueueResponseDTO("유효토큰이 발급되었습니다.", 0L, expirationTime.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        } else {
            queueManager.addToQueue(userRequestDTO.userId());
            long waitInfo = queueManager.getQueuePosition(userRequestDTO.userId());
            return new QueueResponseDTO("대기정보 조회 성공", waitInfo, null);

        }
    }

    public LocalDateTime timeFormatter(String stringTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime.parse(stringTime, formatter);
    }
}

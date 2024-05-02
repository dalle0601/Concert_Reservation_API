package org.example.ticketing.api.usecase.common;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.domain.user.model.Queue;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.service.QueueService;
import org.example.ticketing.domain.user.service.TokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WaitSchedulerUseCase {
    private final TokenService tokenService;
    private final QueueService queueService;

    @Scheduled(fixedRate = 10000)
    public void execute() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Token> expiredRows = tokenService.findByExpiredAtBefore(currentTime);

        if(!expiredRows.isEmpty()) {
            tokenService.deleteExpiredTokens(expiredRows);
            // 대기열에서 가장 빠른 순서대로 delete Tokens의 갯수만큼 사용자를 가져와서 대기열에서 제거하고, 새로운 토큰을 발급
            List<Queue> usersToRemove = queueService.getUsersToRemove(expiredRows.size());
            for(int i = 0; i < expiredRows.size(); i++){
                if(usersToRemove.size() - 1 >= i) {
                    String userUUID = UUID.randomUUID().toString();
//                Token newToken = new Token(usersToRemove.get(i).getUserId(), userUUID, currentTime.plusMinutes(5), true);
                    Token newToken = new Token(usersToRemove.get(i).getUserId(), userUUID, currentTime.plusSeconds(30), true);
                    tokenService.enterToken(newToken);

                    // 토큰이 발급되었는지 확인 후 발급되었으면 대기열 삭제
                    Token issueToken = tokenService.checkToken(new UserRequestDTO(usersToRemove.get(i).getUserId()));
                    if (issueToken != null) {
                        queueService.deleteQueue(usersToRemove.get(i).getUserId());
                    }
                }
            }
        }
    }
}

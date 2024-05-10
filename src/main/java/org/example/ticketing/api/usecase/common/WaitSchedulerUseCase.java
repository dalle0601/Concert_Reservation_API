package org.example.ticketing.api.usecase.common;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaitSchedulerUseCase {
//    private final TokenService tokenService;
//    private final QueueService queueService;

    private final TokenManager tokenManager;
    private final QueueManager queueManager;
    @Scheduled(fixedRate = 3000)
    @Transactional
    public void execute() throws Exception {
//        LocalDateTime currentTime = LocalDateTime.now();
//        List<Token> expiredRows = tokenService.findByExpiredAtBefore(currentTime);
//
//        if(!expiredRows.isEmpty()) {
//            tokenService.deleteExpiredTokens(expiredRows);
//            // 대기열에서 가장 빠른 순서대로 delete Tokens의 갯수만큼 사용자를 가져와서 대기열에서 제거하고, 새로운 토큰을 발급
//            List<Queue> usersToRemove = queueService.getUsersToRemove(expiredRows.size());
//            for(int i = 0; i < expiredRows.size(); i++){
//                if(usersToRemove.size() - 1 >= i) {
//                    String userUUID = UUID.randomUUID().toString();
////                Token newToken = new Token(usersToRemove.get(i).getUserId(), userUUID, currentTime.plusMinutes(5), true);
//                    Token newToken = new Token(usersToRemove.get(i).getUserId(), userUUID, currentTime.plusSeconds(30), true);
//                    tokenService.enterToken(newToken);
//
//                    // 토큰이 발급되었는지 확인 후 발급되었으면 대기열 삭제
//                    Token issueToken = tokenService.checkToken(new UserRequestDTO(usersToRemove.get(i).getUserId()));
//                    if (issueToken != null) {
//                        queueService.deleteQueue(usersToRemove.get(i).getUserId());
//                    }
//                }
//            }
//        }
        Long validTokenCount = tokenManager.getValidTokenCount();
        if (validTokenCount < 3) { // 토큰의 수가 3개 미만일 경우
            Long userId = queueManager.getNextInQueue(); // 큐에서 다음 유저 가져오기
            if (userId != null) { // 큐가 비어있지 않다면
                tokenManager.issueToken(userId); // 해당 유저에게 토큰 발급
                queueManager.removeUserFromQueue(userId); // 대기열에서 사용자 삭제
            }
        }

    }
}

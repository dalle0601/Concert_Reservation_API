package org.example.ticketing.api.usecase;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class IssueUserTokenUseCase {
    private final UserRepository userRepository;

    public IssueUserTokenUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /*
        토큰 발급 로직
     */
    public String execute(UserRequestDTO userRequestDTO) {
        String userUUID = UUID.randomUUID().toString();

        UserInfo userInfo = new UserInfo(userUUID, userRequestDTO.userId());


        // 대기열 관련 정보를 포함한 토큰 생성
        String token = "";
//        String token = tokenWithWaitInfo(userUUID);

        userRepository.joinUser(userUUID, userInfo.getUserId());
        return token;

    }

//    private String tokenokenWithWaitInfo(String userId) {
//        int queueNumber = waitingQueueService.getQueueNumber(userId); // 사용자의 대기 순서 조회
//        long remainingTime = waitingQueueService.getRemainingTime(userId); // 사용자의 대기 시간 조회
//
//        return userId + "-" + queueNumber + "-" + remainingTime;
//    }
}

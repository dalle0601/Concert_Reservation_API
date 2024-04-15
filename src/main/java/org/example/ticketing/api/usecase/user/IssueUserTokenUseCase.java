package org.example.ticketing.api.usecase.user;

import jakarta.persistence.EntityNotFoundException;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.QueueWaitInfoResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.api.usecase.common.UpdateTokenQueueWaitInfo;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.example.ticketing.domain.user.service.QueueService;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
public class IssueUserTokenUseCase {
    private final UserService userService;
    private final QueueService queueService;
    private final UpdateTokenQueueWaitInfo updateTokenQueueWaitInfo;

    public IssueUserTokenUseCase(UserService userService, QueueService queueService, UpdateTokenQueueWaitInfo updateTokenQueueWaitInfo) {
        this.userService = userService;
        this.queueService = queueService;
        this.updateTokenQueueWaitInfo = updateTokenQueueWaitInfo;
    }

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        // # 1 > UserInfo table 에서 user_id 조회
        if(findUser(userRequestDTO.user_id()) == null) {
            // 없으면 User table에 insert 후
            // user_id 와 token을 insert or update
            UserInfo newUser = userService.joinUser(userRequestDTO);
        }

        // # 2 > Queue table 에서 대기열 정보 생성 및 insert or update
        String userUUID = UUID.randomUUID().toString();
        String token = tokenWithWaitInfo(userUUID);
        // 내 대기열 정보를 Queue 테이블에 update or insert
        // # 3 > Token table 에 user_id, token 정보<uuid + / + status> insert or update
        return updateTokenQueueWaitInfo.execute(userRequestDTO, token);
    }
    /*
        Queue Table 전체 내용의
        status = onGoing, onWait 상태인 갯수 가져올 메소드
     */
    private String tokenWithWaitInfo(String userUUID) {
        QueueWaitInfoResponseDTO waitInfo = queueService.findQueueOngoingAndWaitInfo();
        if(waitInfo.onGoing() < 3) {
            return userUUID + "/onGoing";
        } else {
            return userUUID + "/onWait/" + waitInfo.onWait().toString();
        }
    }

    private UserInfo findUser(Long user_id) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        return userService.findUserInfo(userRequestDTO);
    }
}

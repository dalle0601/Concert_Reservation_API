package org.example.ticketing.api.usecase;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.common.TokenQueueTableUpdate;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;
@Service
public class IssueUserTokenUseCase {
    private final UserRepository userRepository;
    private final QueueRepository queueRepository;
    private final TokenQueueTableUpdate tokenQueueTableUpdate;

    public IssueUserTokenUseCase(UserRepository userRepository, QueueRepository queueRepository, TokenQueueTableUpdate tokenQueueTableUpdate) {
        this.userRepository = userRepository;
        this.queueRepository = queueRepository;
        this.tokenQueueTableUpdate = tokenQueueTableUpdate;
    }

    public TokenResponseDTO execute(UserRequestDTO userRequestDTO) {
        /*
            토큰 발급 처음 요청 (user_id)
            # 1 > UserInfo table 에서 user_id 조회
                    > user_id 있으면 다음스텝
                    > user_id 없으면 UserInfo table insert [ userRepository.joinUser(userRequestDTO.user_id()) ]

            # 2 > Queue table 에서 대기열 정보 생성 및 user_id, status 정보<onGoing or onWait> insert or update

            # 3 > Token table 에 user_id, token 정보<uuid + / + status> insert or update

            # 4 loop > polling 이용한 Queue Table 대기열 정보 최신화 및 그에 따른 token 정보값 Token table update
         */

        // # 1 > UserInfo table 에서 user_id 조회
        if(findUser(userRequestDTO.user_id()) == null) {
            // 없으면 User table에 insert 후
            // user_id 와 token을 insert or update
            UserInfo newUser = userRepository.joinUser(userRequestDTO.user_id());
        }

        // # 2 > Queue table 에서 대기열 정보 생성 및 insert or update
        String userUUID = UUID.randomUUID().toString();
        String token = tokenWithWaitInfo(userUUID);
        // 내 대기열 정보를 Queue 테이블에 update or insert
        // # 3 > Token table 에 user_id, token 정보<uuid + / + status> insert or update
        return tokenQueueTableUpdate.execute(userRequestDTO, token);
    }
    /*
        Queue Table 전체 내용의
        status = onGoing, onWait 상태인 갯수 가져올 메소드
     */
    private String tokenWithWaitInfo(String userUUID) {
        Object[] waitInfo = (Object[]) queueRepository.getQueueOngoingAndWaitInfo();
        System.out.println((Long)waitInfo[0]);
        if((Long)waitInfo[0] < 3) {
            return userUUID + "/onGoing";
        } else {
            return userUUID + "/onWait/" + waitInfo[1].toString();
        }
    }

    private UserInfo findUser(Long user_id) {
        return userRepository.findUserByUserId(user_id);
    }
}

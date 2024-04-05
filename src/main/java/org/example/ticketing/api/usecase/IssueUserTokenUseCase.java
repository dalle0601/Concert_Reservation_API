package org.example.ticketing.api.usecase;

//import org.example.ticketing.api.component.WaitPollingTask;
import org.example.ticketing.api.component.polling.QuartzPollingTask;
import org.example.ticketing.api.component.polling.QuartzSchedulingService;
import org.example.ticketing.api.dto.request.UserRequestDTO;
        import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class IssueUserTokenUseCase {
    private final UserRepository userRepository;
    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;
    private final QuartzSchedulingService quartzSchedulingService;
    public IssueUserTokenUseCase(UserRepository userRepository, QueueRepository queueRepository, TokenRepository tokenRepository, QuartzSchedulingService quartzSchedulingService) {
        this.userRepository = userRepository;
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
        this.quartzSchedulingService = quartzSchedulingService;
    }

    public String execute(UserRequestDTO userRequestDTO) {
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
        tokenInsertOrUpdateQueue(userRequestDTO.user_id(), token.split("/")[1]);

        // # 3 > Token table 에 user_id, token 정보<uuid + / + status> insert or update
        tokenRepository.tokenInsertOrUpdate(userRequestDTO.user_id(), token);

        quartzSchedulingService.schedulePollingTask();
        return token;
    }
    /*
        Queue Table 전체 내용의
        status = onGoing, onWait 상태인 갯수 가져올 메소드
     */
    private String tokenWithWaitInfo(String userUUID) {
        Object[] waitInfo = (Object[]) queueRepository.getQueueOngoingAndWaitInfo();
        if((Long)waitInfo[0] < 10) {
            return userUUID + "/onGoing";
        } else {
            return userUUID + "/onWait/" + waitInfo[1].toString();
        }
    }

    /*
        대기열 정보 까지 포함된 토큰 발급 후
        Queue Table에 내 대기열 Insert OR Update
     */
    private void tokenInsertOrUpdateQueue(Long user_id, String status) {
        queueRepository.queueInsertOrUpdate(user_id, status);
    }

    private UserInfo findUser(Long user_id) {
        return userRepository.findUserByUserId(user_id);
    }
}

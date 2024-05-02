package org.example.ticketing.api.usecase.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnterQueueUseCase {
    private final UserService userService;
    private final UpdateQueueUseCase updateQueueUseCase;

    @Transactional
    public QueueResponseDTO execute(UserRequestDTO userRequestDTO) throws Exception {
        UserInfo userInfo = findUser(userRequestDTO.userId());
        if(userInfo == null) {
            userService.joinUser(userRequestDTO);
        }
        return updateQueueUseCase.execute(userRequestDTO);
    }

    private UserInfo findUser(Long userId) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        return userService.findUserInfo(userRequestDTO);
    }
}

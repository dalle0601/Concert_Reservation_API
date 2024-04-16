package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.QueueResponseDTO;
import org.example.ticketing.api.usecase.user.EnterQueueUseCase;
import org.example.ticketing.api.usecase.user.UpdateQueueUseCase;
import org.example.ticketing.domain.user.model.Queue;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.QueueService;
import org.example.ticketing.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnterQueueUseCaseTest {
    private EnterQueueUseCase enterQueueUseCase;
    @Mock
    private UserService userService;
    @Mock
    private QueueService queueService;

    @Mock
    private UpdateQueueUseCase updateQueueUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        enterQueueUseCase = new EnterQueueUseCase(userService, queueService, updateQueueUseCase);
    }

    @DisplayName("유저 대기열 진입요청 > 새로운 유저")
    @Test
    public void executeTest_newUser() throws Exception {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        UserInfo userInfo = new UserInfo(userId);

        when(userService.findUserInfo(any())).thenReturn(null);
        when(userService.joinUser(any())).thenReturn(userInfo);
        when(queueService.findQueueInfo(any())).thenReturn(null);

        QueueResponseDTO responseDTO = enterQueueUseCase.execute(userRequestDTO);

        verify(userService, times(1)).findUserInfo(userRequestDTO);
        verify(userService, times(1)).joinUser(userRequestDTO);
        verify(queueService, times(1)).findQueueInfo(userInfo.getUserId());
        verify(queueService, times(1)).enterQueue(userRequestDTO.user_id());
        verify(updateQueueUseCase, times(1)).execute(userRequestDTO);
    }

    @DisplayName("유저 대기열 진입요청 > 이미 대기열 신청한 유저")
    @Test
    public void executeTest_existUser() throws Exception {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        UserInfo userInfo = new UserInfo(userId);
        Queue queueInfo = new Queue();

        when(userService.findUserInfo(any())).thenReturn(userInfo);
        when(queueService.findQueueInfo(any())).thenReturn(queueInfo);

        QueueResponseDTO responseDTO = enterQueueUseCase.execute(userRequestDTO);

        verify(userService, times(1)).findUserInfo(userRequestDTO);
        verify(userService, never()).joinUser(userRequestDTO);
        verify(queueService, times(1)).findQueueInfo(userInfo.getUserId());
        verify(queueService, never()).enterQueue(userRequestDTO.user_id());
        verify(updateQueueUseCase, times(1)).execute(userRequestDTO);
    }

}

package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.UpdateTokenUseCase;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UpdateTokenUseCaseTest {
    @Mock
    private TokenManager tokenManager;

    @Mock
    private QueueManager queueManager;

    @InjectMocks
    private UpdateTokenUseCase updateTokenUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
        client에서 polling시 요청할 대기열 상태 update 테스트
     */
    @DisplayName("polling 요청으로 유효토큰 자리가 나있으면 대기열의 첫번째 유저를 유효토큰 발급후 대기열에서 삭제")
    @Test
    void execute_test_update_and_issue_token() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);
        when(queueManager.getNextInQueue()).thenReturn(1L);
        when(tokenManager.getValidTokenCount()).thenReturn(2L);

        Map<String, String> tokenValue = new HashMap<>();
        tokenValue.put("token", "valid-token");
        tokenValue.put("expirationTime", "2024-12-31T23:59:59");
        when(tokenManager.issueToken(anyLong())).thenReturn(tokenValue);

        TokenResponseDTO response = updateTokenUseCase.execute(userRequestDTO);

        assertEquals("유효토큰이 발급되었습니다.", response.message());
        assertEquals("valid-token", response.token());
        assertEquals("2024-12-31T23:59:59", response.expiredTime());
    }

    @DisplayName("polling 요청으로 유효토큰 자리는 없지만 대기열의 첫번째 유저인 경우")
    @Test
    void execute_test_update_queue_first() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);
        when(queueManager.getNextInQueue()).thenReturn(1L);
        when(tokenManager.getValidTokenCount()).thenReturn(3L);

        TokenResponseDTO response = updateTokenUseCase.execute(userRequestDTO);

        assertEquals("이제 곧 입장합니다.", response.message());
    }

    @DisplayName("polling 요청으로 유효토큰 자리도 없고, 대기열도 유저 앞에 대기자가 존재하는 경우")
    @Test
    void execute_test_update_queue() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(2L);
        when(queueManager.getNextInQueue()).thenReturn(1L);
        when(queueManager.getQueuePosition(2L)).thenReturn(5);

        TokenResponseDTO response = updateTokenUseCase.execute(userRequestDTO);

        assertEquals("대기상태입니다.", response.message());
        assertEquals("5", response.token());
        assertNull(response.expiredTime());
    }

    @DisplayName("polling 요청으로 대기열정보에도 없는 경우")
    @Test
    void execute_test_no_user_waiting() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(2L);
        when(queueManager.getNextInQueue()).thenReturn(1L);
        when(queueManager.getQueuePosition(2L)).thenReturn(-1);

        TokenResponseDTO response = updateTokenUseCase.execute(userRequestDTO);

        assertEquals("대기열에 사용자가 없습니다.", response.message());
        assertEquals("-1", response.token());
        assertNull(response.expiredTime());
    }


}

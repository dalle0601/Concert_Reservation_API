package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.QueueResponseDTO;
import org.example.ticketing.api.usecase.user.EnterQueueUseCase;
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

public class EnterQueueUseCaseTest {
    @InjectMocks
    private EnterQueueUseCase enterQueueUseCase;
    @Mock
    private TokenManager tokenManager;
    @Mock
    private QueueManager queueManager;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유저 대기열 진입요청 > 대기열 없음 > 유효토큰 발급")
    @Test
    public void execute_test_issue_token() throws Exception {
        Long userId = 1L;

        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        when(tokenManager.getValidTokenCount()).thenReturn(2L);

        Map<String, String> emptyCheckToken = new HashMap<>();
        when(tokenManager.getCheckTokenInfo(anyLong())).thenReturn(emptyCheckToken);

        Map<String, String> tokenValue = new HashMap<>();
        tokenValue.put("expirationTime", "2024-12-31T23:59:59");
        when(tokenManager.issueToken(anyLong())).thenReturn(tokenValue);

        QueueResponseDTO response = enterQueueUseCase.execute(userRequestDTO);

        assertEquals("유효토큰이 발급되었습니다.", response.message());
        assertEquals(0L, response.waitCount());
        assertEquals("2024-12-31T23:59:59", response.expireTime());
    }
    @DisplayName("유저 대기열 진입 요청 > 대기열 없음 > 이미 유효토큰 발급된 상태")
    @Test
    void execute_test_after_issue_token() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);
        when(tokenManager.getValidTokenCount()).thenReturn(2L);

        Map<String, String> checkToken = new HashMap<>();
        checkToken.put("token", "some-token");
        checkToken.put("expirationTime", "2024-12-31T23:59:59");
        when(tokenManager.getCheckTokenInfo(anyLong())).thenReturn(checkToken);

        QueueResponseDTO response = enterQueueUseCase.execute(userRequestDTO);

        assertEquals("이미 유효토큰이 발급되었습니다.", response.message());
        assertEquals(0L, response.waitCount());
        assertEquals("2024-12-31T23:59:59", response.expireTime());
    }

    @DisplayName("유저 대기열 진입요청 > 대기열 있음 > 대기열 정보 반환")
    @Test
    public void execute_test_enter_waiting() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);
        when(tokenManager.getValidTokenCount()).thenReturn(3L);

        when(queueManager.getQueuePosition(anyLong())).thenReturn(5);

        QueueResponseDTO response = enterQueueUseCase.execute(userRequestDTO);

        assertEquals("대기열에 포함되었습니다.", response.message());
        assertEquals(5L, response.waitCount());
        assertNull(response.expireTime());
    }

}

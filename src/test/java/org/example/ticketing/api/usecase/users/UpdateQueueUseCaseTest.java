package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.QueueResponseDTO;
import org.example.ticketing.api.usecase.user.UpdateQueueUseCase;
import org.example.ticketing.domain.user.service.QueueService;
import org.example.ticketing.domain.user.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UpdateQueueUseCaseTest {
    private UpdateQueueUseCase updateQueueUseCase;
    @Mock
    private TokenService tokenService;
    @Mock
    private QueueService queueService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        updateQueueUseCase = new UpdateQueueUseCase(tokenService, queueService);
    }

    @DisplayName("유저 토큰 대기열 업데이트 > 유효토큰 10명일때 > Queue에 대기열 추가 안되어있는 경우 > Queue 대기열 추가")
    @Test
    public void enterQueueTest() throws Exception {
        Long userId = 1L;
        LocalDateTime nowDate = LocalDateTime.now();

        when(tokenService.findTokenCount()).thenReturn(10L);
        when(queueService.findQueueInfo(any())).thenReturn(null);
        when(queueService.findQueueCount(any())).thenReturn(2L);
        QueueResponseDTO responseDTO = updateQueueUseCase.execute(new UserRequestDTO(userId));

        assertEquals("대기중입니다.", responseDTO.message());
        assertEquals(2, responseDTO.waitCount());
    }

    @DisplayName("유저 토큰 대기열 업데이트 > 유효토큰 10명 이하 > Queue 테이블 내 나보다 빠른 유저 없으면 > Token 유효토큰 추가")
    @Test
    public void issueTokenTest() throws Exception {
        Long userId = 1L;
        LocalDateTime nowDate = LocalDateTime.now();

        when(tokenService.findTokenCount()).thenReturn(9L);
        when(queueService.findQueueInfo(any())).thenReturn(null);
        when(queueService.findQueueCount(any())).thenReturn(0L);
        QueueResponseDTO responseDTO = updateQueueUseCase.execute(new UserRequestDTO(userId));

        assertEquals("유효토큰이 발급되었습니다.", responseDTO.message());
    }

    @DisplayName("유저 토큰 대기열 업데이트 > 유효토큰 10명 이하 > Queue 테이블 내 나보다 빠른 유저 있음 > 대기순번 return")
    @Test
    public void waitQueueTest() throws Exception {
        Long userId = 1L;
        LocalDateTime nowDate = LocalDateTime.now();

        when(tokenService.findTokenCount()).thenReturn(9L);
        when(queueService.findQueueInfo(any())).thenReturn(null);
        when(queueService.findQueueCount(any())).thenReturn(2L);
        QueueResponseDTO responseDTO = updateQueueUseCase.execute(new UserRequestDTO(userId));

        assertEquals("대기중입니다.", responseDTO.message());
        assertEquals(2, responseDTO.waitCount());
    }

}

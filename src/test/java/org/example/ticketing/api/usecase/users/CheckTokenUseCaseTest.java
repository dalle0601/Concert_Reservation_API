package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.infrastructure.queue.QueueManager;
import org.example.ticketing.infrastructure.token.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CheckTokenUseCaseTest {
    private CheckTokenUseCase checkTokenUseCase;
//    @Mock
//    private TokenService tokenService;
//    @Mock
//    private UpdateQueueUseCase updateQueueUseCase;
    @Mock
    private TokenManager tokenManager;
    @Mock
    private QueueManager queueManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        checkTokenUseCase = new CheckTokenUseCase(tokenManager, queueManager);
    }

    @DisplayName("유효한 토큰인지 체크 > 유효한 토큰확인됨")
    @Test
    public void executeTest_availableToken() throws Exception {
//        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);
//        Token token = new Token(1L, "tokenValue", LocalDateTime.now().plusMinutes(5), true);
//
//        when(tokenService.checkToken(userRequestDTO)).thenReturn(token);
//
//        TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(userRequestDTO);
//
//        verify(tokenService, times(1)).checkToken(userRequestDTO);
//        verify(updateQueueUseCase, never()).execute(userRequestDTO);
//
//        assertEquals("유효한 토큰입니다.", tokenResponseDTO.message());
//        assertEquals("tokenValue", tokenResponseDTO.token());
    }

    @DisplayName("유효한 토큰인지 체크 > 토큰 확인 불가")
    @Test
    public void executeTest_unavailableToken() throws Exception {
//        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);
//
//        when(tokenService.checkToken(userRequestDTO)).thenReturn(null);
//
//        TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(userRequestDTO);
//
//        verify(tokenService, times(1)).checkToken(userRequestDTO);
//        verify(updateQueueUseCase, times(1)).execute(userRequestDTO);
//
//        assertEquals("토큰이 확인되지 않습니다.", tokenResponseDTO.message());
//        assertEquals(null, tokenResponseDTO.token());
//        assertEquals(null, tokenResponseDTO.expiredTime());
    }
}

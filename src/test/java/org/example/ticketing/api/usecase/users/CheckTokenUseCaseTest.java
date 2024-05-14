package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
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

public class CheckTokenUseCaseTest {
    @InjectMocks
    private CheckTokenUseCase checkTokenUseCase;
    @Mock
    private TokenManager tokenManager;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @DisplayName("유효한 토큰인지 체크 > 유효한 토큰확인됨")
    @Test
    void execute_test_availableToken() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);

        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("token", "valid-token");
        tokenInfo.put("expirationTime", "2024-12-31T23:59:59");
        when(tokenManager.getCheckTokenInfo(anyLong())).thenReturn(tokenInfo);

        TokenResponseDTO response = checkTokenUseCase.execute(userRequestDTO);

        assertEquals("유효한 토큰입니다.", response.message());
        assertEquals("valid-token", response.token());
        assertEquals("2024-12-31T23:59:59", response.expiredTime());
    }
    @DisplayName("유효한 토큰인지 체크 > 토큰 확인 불가")
    @Test
    void execute_test_unavailableToken() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(1L);

        Map<String, String> tokenInfo = new HashMap<>();
        when(tokenManager.getCheckTokenInfo(anyLong())).thenReturn(tokenInfo);

        TokenResponseDTO response = checkTokenUseCase.execute(userRequestDTO);

        assertEquals("유효하지 않은 토큰입니다.", response.message());
        assertNull(response.token());
        assertNull(response.expiredTime());
    }
}

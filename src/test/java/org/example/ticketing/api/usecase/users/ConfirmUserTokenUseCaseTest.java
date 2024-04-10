package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.ConfirmUserTokenUseCase;
import org.example.ticketing.domain.user.model.Token;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConfirmUserTokenUseCaseTest {
    private ConfirmUserTokenUseCase confirmUserTokenUseCase;
    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        confirmUserTokenUseCase = new ConfirmUserTokenUseCase(tokenRepository);

    }

    @DisplayName("토큰 대기열 상태 확인 > 대기중 상태")
    @Test
    public void executeTest_onWait() {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        String expectedTokenOnWaitStatus = "onWait";
        String expectedTokenNum = "4";

        Token token = Token.builder()
                .user_id(userId)
                .token_value("abcd-efgh-ijkl/onWait/4")
                .updated_at(LocalDateTime.now())
                .created_at(LocalDateTime.now()).build();

        when(tokenRepository.findByUserId(any())).thenReturn(token);
        TokenResponseDTO actualToken = confirmUserTokenUseCase.execute(userRequestDTO);

        assertEquals(expectedTokenOnWaitStatus, actualToken.token().split("/")[1]);
        assertEquals(expectedTokenNum, actualToken.token().split("/")[2]);
    }

    @DisplayName("토큰 대기열 상태 확인 > 실행가능상태")
    @Test
    public void executeTest_onGoing() {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        String expectedTokenOnWaitStatus = "onGoing";

        Token token = Token.builder()
                .user_id(userId)
                .token_value("abcd-efgh-ijkl/onGoing")
                .updated_at(LocalDateTime.now())
                .created_at(LocalDateTime.now()).build();
        when(tokenRepository.findByUserId(any())).thenReturn(token);
        TokenResponseDTO actualToken = confirmUserTokenUseCase.execute(userRequestDTO);

        assertEquals(expectedTokenOnWaitStatus, actualToken.token().split("/")[1]);
    }
}

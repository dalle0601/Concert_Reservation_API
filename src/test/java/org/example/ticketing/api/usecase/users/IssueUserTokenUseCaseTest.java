package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.IssueUserTokenUseCase;
import org.example.ticketing.api.usecase.common.TokenQueueTableUpdate;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IssueUserTokenUseCaseTest {
    private IssueUserTokenUseCase issueUserTokenUseCase;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QueueRepository queueRepository;

    @Mock
    private TokenQueueTableUpdate tokenQueueTableUpdate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        issueUserTokenUseCase = new IssueUserTokenUseCase(userRepository, queueRepository, tokenQueueTableUpdate);
    }

    @DisplayName("유저 토큰 발급 - 처음 발급")
    @Test
    public void executeTest() {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        String expectedTokenOnWaitStatus = "onWait";
        String expectedTokenNum = "4";

        when(userRepository.findUserByUserId(anyLong())).thenReturn(null);
        when(userRepository.joinUser(anyLong())).thenReturn(new UserInfo());
        Object[] mockWaitInfo = {10L, 4L};
        when(queueRepository.getQueueOngoingAndWaitInfo()).thenReturn(mockWaitInfo);
        when(tokenQueueTableUpdate.execute(any(), any())).thenReturn(new TokenResponseDTO("abc-def-ghi/onWait/4"));
        TokenResponseDTO actualToken = issueUserTokenUseCase.execute(userRequestDTO);

        assertEquals(expectedTokenOnWaitStatus, actualToken.token().split("/")[1]);
        assertEquals(expectedTokenNum, actualToken.token().split("/")[2]);
    }

}

package org.example.ticketing.api.usecase.users;

import org.example.ticketing.api.component.polling.QuartzSchedulingService;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.QueueWaitInfoResponseDTO;
import org.example.ticketing.api.usecase.IssueUserTokenUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.QueueRepository;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class IssueUserTokenUseCaseTest {
    private IssueUserTokenUseCase issueUserTokenUseCase;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QueueRepository queueRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private QuartzSchedulingService quartzSchedulingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        issueUserTokenUseCase = new IssueUserTokenUseCase(userRepository, queueRepository, tokenRepository, quartzSchedulingService);
    }

    @DisplayName("execute 메소드 테스트")
    @Test
    public void executeTest() {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
        String expectedTokenStatus = "onGoing";

        when(userRepository.findUserByUserId(userId)).thenReturn(null); // Simulating user not found
        when(userRepository.joinUser(userId)).thenReturn(new UserInfo()); // Simulating new user joined
        when(queueRepository.getQueueOngoingAndWaitInfo()).thenReturn(new QueueWaitInfoResponseDTO(0L, 0L));
        String actualToken = issueUserTokenUseCase.execute(userRequestDTO);

        assertEquals(expectedTokenStatus, actualToken.split("/")[1]);
    }

}

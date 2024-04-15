package org.example.ticketing.api.usecase.points;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.api.usecase.common.UpdateTokenQueueStatus;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetPointUseCaseTest {
    private GetPointUseCase getPointUseCase;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getPointUseCase = new GetPointUseCase(userRepository);
    }
    @Test
    @DisplayName("잔액 확인 테스트")
    void amountPointTest() {
        Long user_id = 1L;
        when(userRepository.findUserByUserId(any())).thenReturn(new UserInfo(user_id, 1000L));
        UserInfo acturePoint = getPointUseCase.execute(user_id);
        assertEquals(1000L, acturePoint.getPoint());
    }

   
}

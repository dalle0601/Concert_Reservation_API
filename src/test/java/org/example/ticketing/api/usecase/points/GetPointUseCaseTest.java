package org.example.ticketing.api.usecase.points;

import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.repository.UserRepository;
import org.example.ticketing.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetPointUseCaseTest {
    private GetPointUseCase getPointUseCase;
    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getPointUseCase = new GetPointUseCase(userService);
    }

    @Test
    @DisplayName("잔액 확인 테스트 > 없는 유저 조회하는 경우")
    void noneUserSearchPointTest() {
        Long userId = 1L;
        when(userService.findUserInfo(any())).thenReturn(null);
        PointResponseDTO actualValue = getPointUseCase.execute(userId);
        assertEquals("해당 유저가 존재하지 않습니다.", actualValue.message());
        assertEquals(null, actualValue.userId());
        assertEquals(null, actualValue.point());
    }

    @Test
    @DisplayName("잔액 확인 테스트")
    void amountPointTest() {
        Long userId = 1L;
        Long point = 1000L;
        when(userService.findUserInfo(any())).thenReturn(new UserInfo(userId, point));
        PointResponseDTO actualValue = getPointUseCase.execute(userId);
        assertEquals("유저 정보 조회 성공", actualValue.message());
        assertEquals(1L, actualValue.userId());
        assertEquals(1000L, actualValue.point());
    }
}
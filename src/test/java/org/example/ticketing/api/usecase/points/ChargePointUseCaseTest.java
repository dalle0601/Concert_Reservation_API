package org.example.ticketing.api.usecase.points;

import org.example.ticketing.api.dto.point.reqeust.PointRequestDTO;
import org.example.ticketing.api.dto.point.response.PointHistorySaveResponseDTO;
import org.example.ticketing.api.usecase.point.ChargePointUseCase;
import org.example.ticketing.api.usecase.point.WritePointHistoryUseCase;
import org.example.ticketing.domain.pointHistory.model.PointHistory;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ChargePointUseCaseTest {
    private ChargePointUseCase chargePointUseCase;
    @Mock
    private UserService userService;
    @Mock
    private WritePointHistoryUseCase writePointHistoryUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        chargePointUseCase = new ChargePointUseCase(userService, writePointHistoryUseCase);
    }

    @Test
    @DisplayName("포인트 충전 테스트 > 없는 유저의 포인트 충전하는 경우")
    void noneUserChargePointTest() {
        Long userId = 1L;
        Long point = 2000L;

        when(userService.findUserInfo(any())).thenReturn(null);

        PointHistorySaveResponseDTO actualValue = chargePointUseCase.execute(new PointRequestDTO(userId, point));

        assertEquals("해당 유저가 존재하지 않습니다.", actualValue.message());
        assertEquals(null, actualValue.pointHistory());
    }

    @Test
    @DisplayName("포인트 충전 테스트 > 0 혹은 마이너스 포인트 충전하는 경우")
    void zeroOrMinusPointChargeTest() {
        Long userId = 1L;
        Long point = -1000L;

        when(userService.findUserInfo(any())).thenReturn(new UserInfo(1L, userId, point, LocalDateTime.now()));

        PointHistorySaveResponseDTO actualValue = chargePointUseCase.execute(new PointRequestDTO(userId, point));

        assertEquals("0 또는 마이너스 포인트는 충전 불가능합니다.", actualValue.message());
        assertEquals(null, actualValue.pointHistory());
    }

    @Test
    @DisplayName("포인트 충전 테스트 > 포인트 충전 성공")
    void chargePointTest() {
        Long userId = 1L;
        Long point = 1000L;
        String status = "charge";

        when(userService.findUserInfo(any())).thenReturn(new UserInfo(1L, userId, point, LocalDateTime.now()));
        when(userService.chargePoint(any())).thenReturn(new UserInfo(1L, userId, 2000L, LocalDateTime.now()));
        when(writePointHistoryUseCase.execute(any())).thenReturn(new PointHistorySaveResponseDTO("포인트 내역 저장 성공", new PointHistory(1L, 2000L, "charge")));
        PointHistorySaveResponseDTO actualValue = chargePointUseCase.execute(new PointRequestDTO(userId, point));

        assertEquals("포인트 충전 성공", actualValue.message());
        assertEquals(2000L, actualValue.pointHistory().getPoint());
        assertEquals(status, actualValue.pointHistory().getStatus());
    }
}
package org.example.ticketing.api.usecase.points;

import org.example.ticketing.api.dto.point.reqeust.PointHistorySaveRequestDTO;
import org.example.ticketing.api.dto.point.response.PointHistorySaveResponseDTO;
import org.example.ticketing.api.usecase.point.WritePointHistoryUseCase;
import org.example.ticketing.domain.point.model.PointHistory;
import org.example.ticketing.domain.point.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WritePointHistoryUseCaseTest {
    private WritePointHistoryUseCase writePointHistoryUseCase;
    @Mock
    private PointService pointService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        writePointHistoryUseCase = new WritePointHistoryUseCase(pointService);
    }

    @Test
    @DisplayName("포인트 내역 추가 테스트")
    void noneUserSearchPointTest() {
        Long userId = 1L;
        Long point = 2000L;
        String status = "charge";

        PointHistory pointHistory = new PointHistory(1L, userId, point, status, LocalDateTime.now());
        when(pointService.save(any())).thenReturn(pointHistory);

        PointHistorySaveResponseDTO actualValue = writePointHistoryUseCase.execute(new PointHistorySaveRequestDTO(userId, point, status));

        assertEquals("포인트 내역 저장 성공", actualValue.message());
        assertEquals(point, actualValue.pointHistory().getPoint());
        assertEquals(status, actualValue.pointHistory().getStatus());
    }
}
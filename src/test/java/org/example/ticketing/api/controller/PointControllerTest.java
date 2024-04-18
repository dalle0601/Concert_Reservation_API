package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.response.PointHistorySaveResponseDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.dto.response.UserResponseDTO;
import org.example.ticketing.api.usecase.point.ChargePointUseCase;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.domain.point.model.PointHistory;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointControllerTest {
    private MockMvc mockMvc;
    @Mock
    private GetPointUseCase getPointUseCase;
    @Mock
    private ChargePointUseCase chargePointUseCase;
    @InjectMocks
    private PointController pointController;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(pointController)
                .build();
    }

    @DisplayName("포인트 충전 API")
    @Test
    public void chargePointTest() throws Exception {
        Long userId = 1L;
        Long point = 2000L;
        String message = "포인트 충전 성공";
        String status = "charge";

        when(chargePointUseCase.execute(any())).thenReturn(new PointHistorySaveResponseDTO(message, new PointHistory(1L, userId, point,status, LocalDateTime.now())));;
        mockMvc.perform(MockMvcRequestBuilders.patch("/point/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 1, \"point\": 3000}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("포인트 충전 성공")) // 응답 본문에서 message 값이 올바른지 확인합니다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.pointHistory.userId").value(userId)) // 응답 본문에서 pointHistory의 userId 값이 올바른지 확인합니다.
                .andExpect(MockMvcResultMatchers.jsonPath("$.pointHistory.point").value(point))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pointHistory.status").value(status));

    }

    @DisplayName("포인트 조회 API")
    @Test
    public void getPointAmountTest() throws Exception {
        Long userId = 1L;
        Long point = 5000L;
        String message = "유저 정보 조회 성공";
        when(getPointUseCase.execute(any())).thenReturn(new PointResponseDTO(message, userId, point));
        mockMvc.perform(MockMvcRequestBuilders.get("/point/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.point").value(point));
    }

    @DisplayName("결제 API")
    @Test
    public void postTempReservationTest() throws Exception {
        /*
            - 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
            - 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
            - 배정 시간 내에 결제가 완료되지 않았다면 좌석에 대한 임시 배정은 해제되어야 합니다.
            - 배정 시간 내에는 다른 사용자는 예약할 수 없어야 합니다.
         */

    }


}

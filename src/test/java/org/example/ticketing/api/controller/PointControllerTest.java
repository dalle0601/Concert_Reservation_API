package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.response.PaymentInfoDTO;
import org.example.ticketing.api.dto.response.PaymentResponseDTO;
import org.example.ticketing.api.dto.response.PointHistorySaveResponseDTO;
import org.example.ticketing.api.dto.response.PointResponseDTO;
import org.example.ticketing.api.usecase.point.ChargePointUseCase;
import org.example.ticketing.api.usecase.point.GetPointUseCase;
import org.example.ticketing.api.usecase.point.PaymentUseCase;
import org.example.ticketing.domain.point.model.PointHistory;
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
    @Mock
    private PaymentUseCase paymentUseCase;
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
        Long userId = 1L;
        Long reservationId = 1L;
        String message = "결제 완료";

        PaymentInfoDTO paymentInfoDTO = new PaymentInfoDTO(userId, reservationId, 1L, 1L, 50000L, "reserved", LocalDateTime.now());
        when(paymentUseCase.execute(any())).thenReturn(new PaymentResponseDTO(message, paymentInfoDTO));
        mockMvc.perform(MockMvcRequestBuilders.post("/point/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reservationId\": 1, \"userId\": 1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentInfoDTO.seatId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentInfoDTO.status").value("reserved"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentInfoDTO.cost").value(50000L));


    }


}

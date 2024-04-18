package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {
    private MockMvc mockMvc;
    @Mock
    private MakeReservationUseCase makeReservationUseCase;
    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(reservationController)
                .build();
    }

    @DisplayName("좌석 예약 요청 API")
    @Test
    public void reservationConcertTest() throws Exception {
        Long userId = 1L;
        Long concertId = 1L;
        Long seatId = 1L;
        Long cost = 50000L;
        LocalDateTime reservation_time = LocalDateTime.now();

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "1");

        when(makeReservationUseCase.execute(any())).thenReturn(new ReservationResponseDTO("좌석 예약 성공", new Reservation(userId, concertId, seatId, "temporary", 50000L, reservation_time, reservation_time.plusMinutes(5))));

        mockMvc.perform(MockMvcRequestBuilders.post("/reservation")
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"concertId\":1,\"seatId\":1,\"userId\":1,\"cost\":50000}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("좌석 예약 성공"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation.seatId").value(seatId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation.concertId").value(concertId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation.status").value("temporary"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservation.cost").value(cost));
    }
}

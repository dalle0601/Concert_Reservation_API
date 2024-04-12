package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.service.ConcertService;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ReservationService reservationService;
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
        Long reservation_id = 1L;
        Long user_id = 1L;
        Long concert_id = 1L;
        Long seat_id = 1L;
        Long cost = 80000L;
        String seat_status = "reserved";
        LocalDateTime reservation_time = LocalDateTime.now();
        LocalDateTime expiration_time = LocalDateTime.now().plusMinutes(5);
        LocalDateTime created_at = LocalDateTime.now();

        ReservationResponseDTO reservation = new ReservationResponseDTO(user_id, concert_id, seat_id, cost, seat_status, reservation_time, expiration_time);

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1"); // user_id를 문자열에서 Long으로 변환하여 사용

        when(reservationService.reservationConcert(any(), any())).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.patch("/reservation")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"concert_id\":0,\"seat_id\":0,\"user_id\":1,\"reservation_time\":\"2024-04-12T06:22:23.226Z\",\"expiration_time\":\"2024-04-12T06:22:23.226Z\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.concert_id").value(concert_id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seat_id").value(seat_id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost").value(cost));
    }


}

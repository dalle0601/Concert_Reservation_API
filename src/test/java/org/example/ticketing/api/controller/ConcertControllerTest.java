package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConcertControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ConcertService concertService;
    @InjectMocks
    private ConcertController concertController;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(concertController)
                .build();
    }

    @DisplayName("예약 가능 콘서트 날짜 조회")
    @Test
    public void GetAvailableConcertDateTest() throws Exception {
        List<Concert> concerts = new ArrayList<>();
        concerts.add(new Concert(1L, "첫번째 콘서트", LocalDateTime.of(2024, 5, 11, 17, 30), 50L, 14L, LocalDateTime.now()));
        concerts.add(new Concert(2L, "22번째 콘서트", LocalDateTime.of(2024, 6, 6, 18, 30), 50L, 1L, LocalDateTime.now()));
        concerts.add(new Concert(3L, "333번째 콘서트", LocalDateTime.of(2024, 7, 17, 17, 00), 50L, 4L, LocalDateTime.now()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        when(concertService.getConcertDate(any())).thenReturn(concerts);

        mockMvc.perform(MockMvcRequestBuilders.get("/concert/date").headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @DisplayName("예약 가능 콘서트의 좌석 조회")
    @Test
    public void GetAvailableConcertSeatTest() throws Exception {
        List<Seat> seats = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            seats.add(new Seat((long) i, 1L, "A"+i+1, 70000L,"available"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", "1");

        when(concertService.getConcertSeat(any(),any())).thenReturn(seats);

        mockMvc.perform(MockMvcRequestBuilders.get("/concert/1/seat").headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
    }

}

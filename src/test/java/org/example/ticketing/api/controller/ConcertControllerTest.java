package org.example.ticketing.api.controller;

import org.example.ticketing.api.dto.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.response.SeatDTO;
import org.example.ticketing.api.dto.response.SeatResponseDTO;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableDateUseCase;
import org.example.ticketing.api.usecase.concert.GetConcertAvailableSeatUseCase;
import org.example.ticketing.domain.concert.model.Concert;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConcertControllerTest {
    private MockMvc mockMvc;
    @Mock
    private GetConcertAvailableDateUseCase getConcertAvailableDateUseCase;
    @Mock
    private GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase;
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
        concerts.add(new Concert(1L, "첫번째 콘서트", LocalDateTime.of(2024, 5, 11, 17, 30), LocalDateTime.now()));
        concerts.add(new Concert(2L, "22번째 콘서트", LocalDateTime.of(2024, 6, 6, 18, 30), LocalDateTime.now()));
        concerts.add(new Concert(3L, "333번째 콘서트", LocalDateTime.of(2024, 7, 17, 17, 00), LocalDateTime.now()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "1");

        ConcertResponseDTO actualValue = new ConcertResponseDTO("이용가능한 콘서트 날짜 조회 성공", concerts);
        when(getConcertAvailableDateUseCase.execute(any())).thenReturn(actualValue);

        mockMvc.perform(MockMvcRequestBuilders.get("/concert/date").headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(actualValue.message()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.concertList", hasSize(3)));
    }

    @DisplayName("예약 가능 콘서트의 좌석 조회")
    @Test
    public void GetAvailableConcertSeatTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", "1");

        List<SeatDTO> expectSeatList = new ArrayList<>();
        for(int i = 1; i <= 25; i++){
            if(i*2 < 25){
                expectSeatList.add(new SeatDTO((long)i*2, "A"+i*2, 50000L, "available"));
            } else {
                expectSeatList.add(new SeatDTO((long)i*2, "B"+((i*2)-25), 45000L, "available"));
            }
        }

        SeatResponseDTO actualValue = new SeatResponseDTO("이용가능한 콘서트 날짜 조회 성공", expectSeatList);
        when(getConcertAvailableSeatUseCase.execute(any(), any())).thenReturn(actualValue);

        mockMvc.perform(MockMvcRequestBuilders.get("/concert/1/seat").headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(actualValue.message()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatList", hasSize(25)));
    }

}

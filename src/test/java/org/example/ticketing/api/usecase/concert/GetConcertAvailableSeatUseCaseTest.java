package org.example.ticketing.api.usecase.concert;

import org.example.ticketing.api.dto.request.SeatReqeustDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetConcertAvailableSeatUseCaseTest {
    private GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase;

    @Mock
    private ConcertRepository concertRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getConcertAvailableSeatUseCase = new GetConcertAvailableSeatUseCase(concertRepository);

    }
    @Test
    @DisplayName("예약가능한 날짜의 콘서트 좌석을 호출했지만 대기상태가 expired 경우 ->> 예외발생")
    void getAvailableDateTestOnWait() {
//        Long userId = 1L;
//        Long concertId = 1L;
//        when(confirmQueueUseCase.execute(any())).thenReturn(new TokenResponseDTO("abcd-efgh-jklm/expired"));
//        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
//            getConcertAvailableSeatUseCase.execute(new UserRequestDTO(userId), new SeatReqeustDTO(concertId));
//        });
//        assertEquals("토큰이 만료되었습니다.", exception.getMessage());

    }
    @Test
    @DisplayName("예약가능한 날짜의 콘서트 좌석을 호출했고 대기상태가 onGoing 경우")
    void getAvailableDateTestOnGoing() {
//        Long userId = 1L;
//        Long concertId = 1L;
//        when(confirmQueueUseCase.execute(any())).thenReturn(new TokenResponseDTO("abcd-efgh-jklm/onGoing"));
//        List<Seat> seats = new ArrayList<>();
//
//        for(int i = 0; i < 4; i++){
//            seats.add(new Seat((long) i, 1L, "A"+i+1, 70000L,"availble"));
//        }
//
//        when(concertRepository.getConcertSeatById(any())).thenReturn(seats);
//
//        List<Seat> result = getConcertAvailableSeatUseCase.execute(new UserRequestDTO(userId), new SeatReqeustDTO(concertId));
//        assertNotNull(result);
//        assertEquals(4, result.size());
    }
}

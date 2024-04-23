package org.example.ticketing.api.usecase.concert;

import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.SeatResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetConcertAvailableSeatUseCaseTest {
    private GetConcertAvailableSeatUseCase getConcertAvailableSeatUseCase;
    @Mock
    private CheckTokenUseCase checkTokenUseCase;
    @Mock
    private ReservationService reservationService;
    @Mock
    private ConcertService concertService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getConcertAvailableSeatUseCase = new GetConcertAvailableSeatUseCase(checkTokenUseCase, reservationService, concertService);
    }
    @Test
    @DisplayName("예약가능한 날짜의 콘서트 좌석을 호출했지만 대기상태가 expired 경우 ->> 예외발생")
    void getAvailableDateTestOnWait() throws Exception {
        Long userId = 1L;
        Long concertId = 1L;
        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("토큰이 확인되지 않습니다.", null, null));

        SeatResponseDTO actureValue = getConcertAvailableSeatUseCase.execute(new UserRequestDTO(userId), concertId);

        assertEquals("토큰이 유효하지 않습니다.", actureValue.message());
        assertEquals(null, actureValue.seatList());

    }
    @Test
    @DisplayName("예약가능한 날짜의 콘서트 좌석을 호출했고 대기상태가 onGoing 경우")
    void getAvailableDateTestOnGoing() throws Exception {
        Long userId = 1L;
        Long concertId = 1L;
        List<Reservation> seatList = new ArrayList<>();
        for(int i = 1; i <= 25; i++){
            seatList.add(new Reservation((long)i*2, (long)i*2, (long)i*2, "reserved", 50000L, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5)));
        }
        Concert concert = new Concert(1L, "첫번째콘서트", LocalDateTime.now(),LocalDateTime.now());

        when(concertService.findByConcertId(any())).thenReturn(concert);
        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효한 토큰입니다.", "abcd-efgh-ijkl", LocalDateTime.now().plusMinutes(5)));
        when(reservationService.findReservedOrTempSeat(any(), any())).thenReturn(seatList);
        SeatResponseDTO actureValue = getConcertAvailableSeatUseCase.execute(new UserRequestDTO(userId), concertId);

        assertEquals("이용가능한 콘서트 좌석 조회 성공", actureValue.message());
        assertEquals(25, actureValue.seatList().size());
    }
}

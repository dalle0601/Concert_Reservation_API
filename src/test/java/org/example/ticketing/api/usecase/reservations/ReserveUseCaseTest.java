package org.example.ticketing.api.usecase.reservations;

import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.reservation.ReserveUseCase;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ReserveUseCaseTest {
    @Mock
    private ReservationService reservationService;

    @Mock
    private ConcertService concertService;

    @Mock
    private CheckTokenUseCase checkTokenUseCase;

    @InjectMocks
    private ReserveUseCase reserveUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("예약 진행중 토큰이 유효하지 않은경우")
    @Test
    void execute_test_token_not_valid() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1L, 1L, 1L, 100L);

        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효하지 않은 토큰입니다.", null, null));

        ReservationResponseDTO response = reserveUseCase.reserve(reservationRequestDTO);

        assertEquals("토큰이 유효하지 않습니다.", response.message());
        assertNull(response.reservation());
    }

    @DisplayName("예약 진행중 없는 콘서트를 예매할 경우")
    @Test
    void execute_test_no_concert_reservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1L, 1L, 1L, 100L);

        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효한 토큰입니다.", "valid-token", "2024-12-31T23:59:59"));
        when(concertService.findByConcertId(anyLong())).thenReturn(null);

        ReservationResponseDTO response = reserveUseCase.reserve(reservationRequestDTO);

        assertEquals("해당 콘서트는 존재하지 않습니다.", response.message());
        assertNull(response.reservation());
    }

    @DisplayName("예약 진행중 좌석의 상태가 available이 아닌경우")
    @Test
    void execute_test_seat_status_is_not_available() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1L, 1L, 1L, 100L);
        Concert concert = new Concert();

        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효한 토큰입니다.", "valid-token", "2024-12-31T23:59:59"));
        when(concertService.findByConcertId(anyLong())).thenReturn(concert);
        when(reservationService.findNonAvailableByConcertIdAndSeatId(anyLong(), anyLong())).thenReturn(null);

        ReservationResponseDTO response = reserveUseCase.reserve(reservationRequestDTO);

        assertEquals("해당 좌석은 예약할 수 없습니다.", response.message());
        assertNull(response.reservation());
    }

    @DisplayName("좌석 예약 진행 성공")
    @Test
    void execute_test_success_reservation() {
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(1L, 1L, 1L, 100L);
        Concert concert = new Concert();
        Reservation reservation = new Reservation(1L, 1L, 1L, "temporary", 100L, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));

        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효한 토큰입니다.", "valid-token", "2024-12-31T23:59:59"));
        when(concertService.findByConcertId(anyLong())).thenReturn(concert);
        when(reservationService.findNonAvailableByConcertIdAndSeatId(anyLong(), anyLong())).thenReturn(null);
        when(reservationService.save(any(Reservation.class))).thenReturn(reservation);

        ReservationResponseDTO response = reserveUseCase.reserve(reservationRequestDTO);

        assertEquals("좌석 예약 성공", response.message());
        assertEquals(reservation, response.reservation());
    }

}

package org.example.ticketing.api.usecase.reservations;

import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.api.usecase.reservation.ReserveUseCase;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.example.ticketing.domain.user.service.TokenService;
import org.example.ticketing.infrastructure.lock.DistributedLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MakeReservationUseCaseTest {
    private MakeReservationUseCase makeReservationUseCase;

    @Mock
    private CheckTokenUseCase checkTokenUseCase;
    @Mock
    private ReservationService reservationService;
    @Mock
    private ConcertService concertService;
    @Mock
    private TokenService tokenService;
    @Mock
    private DistributedLock distributedLock;

    @Mock
    private ReserveUseCase reserveUseCase;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        makeReservationUseCase = new MakeReservationUseCase(distributedLock, reserveUseCase);
    }

    @DisplayName("토큰 확인 후 성공하면 콘서트 ID, 좌석으로 예약신청 한다")
    @Test
    void postReservationTest() throws Exception {
        Long userId = 1L;
        Long concertId = 1L;
        Long seatId = 1L;
        Long cost = 50000L;
        LocalDateTime reservation_time = LocalDateTime.now();
        // 토큰 확인 후
        // 파라미터로 넘겨받은 userId, concertId, seatId 로 예약을 진행하자.
        when(checkTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("유효한 토큰입니다.", "abcd-efgh-ijkl", LocalDateTime.now().plusMinutes(5)));
        when(concertService.findByConcertId(any())).thenReturn(new Concert(1L, "첫번째콘서트", LocalDateTime.now(), LocalDateTime.now()));
        when(reservationService.save(any())).thenReturn(new Reservation(userId, concertId, seatId, "temporary", cost, reservation_time, reservation_time.plusMinutes(5)));
        when(distributedLock.tryLock(anyString(),anyLong(),anyLong(),any())).thenReturn(true);

        ReservationResponseDTO actualValue = makeReservationUseCase.execute(new ReservationRequestDTO(concertId, seatId, userId, cost));
        assertEquals("좌석 예약 성공", actualValue.message());
        assertEquals(userId, actualValue.reservation().getUserId());
        assertEquals(concertId, actualValue.reservation().getConcertId());
        assertEquals(seatId, actualValue.reservation().getSeatId());
        assertEquals("temporary", actualValue.reservation().getStatus());

    }

}

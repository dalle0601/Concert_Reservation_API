package org.example.ticketing.api.usecase.reservations;

import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.api.usecase.reservation.ReserveUseCase;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.infrastructure.lock.DistributedLock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MakeReservationUseCaseTest {
    @InjectMocks
    private MakeReservationUseCase makeReservationUseCase;
    @Mock
    private DistributedLock distributedLock;
    @Mock
    private ReserveUseCase reserveUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("lock획득 > 트랜잭션 (reservateUseCase) > 비즈니스로직 수행 > 트랜잭션 해제, 락 반환 로직 수행 성공")
    @Test
    void execute_test_reservation_success() throws Exception {
        Long userId = 1L;
        Long concertId = 1L;
        Long seatId = 1L;
        Long cost = 50000L;
        LocalDateTime reservation_time = LocalDateTime.now();

        when(distributedLock.tryLock(anyString(),anyLong(),anyLong(),any())).thenReturn(true);
        when(reserveUseCase.reserve(any())).thenReturn(new ReservationResponseDTO("좌석 예약 성공", new Reservation(1L, userId, concertId, seatId, "temporary", cost, reservation_time, reservation_time.plusMinutes(5))));

        ReservationResponseDTO actualValue = makeReservationUseCase.execute(new ReservationRequestDTO(concertId, seatId, userId, cost));
        assertEquals("좌석 예약 성공", actualValue.message());
        assertEquals(userId, actualValue.reservation().getUserId());
        assertEquals(concertId, actualValue.reservation().getConcertId());
        assertEquals(seatId, actualValue.reservation().getSeatId());
        assertEquals("temporary", actualValue.reservation().getStatus());

    }

}

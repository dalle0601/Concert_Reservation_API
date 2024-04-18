package org.example.ticketing.api.usecase.reservations;

import org.example.ticketing.api.usecase.common.ChangeSeatStatus;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

public class MakeReservationUseCaseTest {
    private MakeReservationUseCase makeReservationUseCase;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ChangeSeatStatus changeSeatStatus;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        makeReservationUseCase = new MakeReservationUseCase(reservationRepository, changeSeatStatus);
    }

    @DisplayName("토큰 확인 후 성공하면 콘서트 ID, 선택된 날짜,좌석으로 예약신청 한다")
    @Test
    void postReservationTest() {
//        Long userId = 1L;
//        Long concert_id = 1L;
//        Long seat_id = 1L;
//        LocalDateTime reservation_time = LocalDateTime.now();
//        UserRequestDTO userRequestDTO = new UserRequestDTO(userId);
//        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(userId, concert_id, seat_id, reservation_time, reservation_time.plusMinutes(5));
//
//        when(confirmQueueUseCase.execute(any())).thenReturn(new TokenResponseDTO("abcd-efgh-jklm/onGoing"));
//
//        Reservation expectedReservation = new Reservation(1L, 1L, 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), LocalDateTime.now());
//        when(reservationRepository.reservationConcert(any())).thenReturn(expectedReservation);
//
//        when(changeSeatStatus.execute(any(), any())).thenReturn(new Seat(seat_id, concert_id, "A1", 80000L, "reserved"));
//        ReservationResponseDTO actualReservation = makeReservationUseCase.execute(userRequestDTO, reservationRequestDTO);
//
//        assertAll("Reservation",
//                () -> assertNotNull(actualReservation),
//                () -> assertEquals(expectedReservation.getUserId(), actualReservation.userId()),
//                () -> assertEquals(expectedReservation.getConcertId(), actualReservation.concert_id()),
//                () -> assertEquals(expectedReservation.getSeatId(), actualReservation.seat_id()),
//                () -> assertEquals(expectedReservation.getReservationTime(), actualReservation.reservation_time()),
//                () -> assertEquals(expectedReservation.getExpirationTime(), actualReservation.reservation_deadline())
//        );

    }

}

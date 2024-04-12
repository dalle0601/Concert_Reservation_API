package org.example.ticketing.api.usecase.reservations;

import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.common.ChangeSeatStatus;
import org.example.ticketing.api.usecase.common.UpdateTokenQueueStatus;
import org.example.ticketing.api.usecase.reservation.MakeReservationUseCase;
import org.example.ticketing.api.usecase.user.ConfirmUserTokenUseCase;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MakeReservationUseCaseTest {
    private MakeReservationUseCase makeReservationUseCase;
    @Mock
    private ConfirmUserTokenUseCase confirmUserTokenUseCase;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ChangeSeatStatus changeSeatStatus;
    @Mock
    private UpdateTokenQueueStatus updateTokenQueueStatus;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        makeReservationUseCase = new MakeReservationUseCase(confirmUserTokenUseCase, reservationRepository, changeSeatStatus, updateTokenQueueStatus);
    }

    @DisplayName("토큰 확인 후 성공하면 콘서트 ID, 선택된 날짜,좌석으로 예약신청 한다")
    @Test
    void postReservationTest() {
        Long user_id = 1L;
        Long concert_id = 1L;
        Long seat_id = 1L;
        LocalDateTime reservation_time = LocalDateTime.now();
        UserRequestDTO userRequestDTO = new UserRequestDTO(user_id);
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO(user_id, concert_id, seat_id, reservation_time, reservation_time.plusMinutes(5));

        when(confirmUserTokenUseCase.execute(any())).thenReturn(new TokenResponseDTO("abcd-efgh-jklm/onGoing"));

        Reservation expectedReservation = new Reservation(1L, 1L, 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), LocalDateTime.now());
        when(reservationRepository.reservationConcert(any())).thenReturn(expectedReservation);

        when(changeSeatStatus.execute(any(), any())).thenReturn(new Seat(seat_id, concert_id, "A1", 80000L, "reserved"));
        ReservationResponseDTO actualReservation = makeReservationUseCase.execute(userRequestDTO, reservationRequestDTO);

        assertAll("Reservation",
                () -> assertNotNull(actualReservation),
                () -> assertEquals(expectedReservation.getUserId(), actualReservation.user_id()),
                () -> assertEquals(expectedReservation.getConcertId(), actualReservation.concert_id()),
                () -> assertEquals(expectedReservation.getSeatId(), actualReservation.seat_id()),
                () -> assertEquals(expectedReservation.getReservationTime(), actualReservation.reservation_time()),
                () -> assertEquals(expectedReservation.getExpirationTime(), actualReservation.reservation_deadline())
        );

    }

}

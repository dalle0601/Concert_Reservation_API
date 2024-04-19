package org.example.ticketing.api.usecase.points;

import org.example.ticketing.api.dto.request.PaymentRequestDTO;
import org.example.ticketing.api.dto.response.PaymentResponseDTO;
import org.example.ticketing.api.usecase.point.PaymentUseCase;
import org.example.ticketing.api.usecase.point.WritePointHistoryUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PaymentUseCaseTest {
    private PaymentUseCase paymentUseCase;
    @Mock
    private ReservationService reservationService;
    @Mock
    private UserService userService;
    @Mock
    private ConcertService concertService;
    @Mock
    private WritePointHistoryUseCase writePointHistoryUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        paymentUseCase = new PaymentUseCase(reservationService, userService, concertService, writePointHistoryUseCase);
    }
    @Test
    @DisplayName("결제 테스트 > 성공")
    void noneUserSearchPointTest() {
        Long userId = 1L;
        Long reservationId = 1L;
        Long concertId = 1L;
        Long seatId = 1L;
        String status = "available";
        Long cost = 50000L;
        LocalDateTime reservationTime = LocalDateTime.now();
        LocalDateTime expirationTime = reservationTime.plusMinutes(5);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userId, reservationId);

        when(reservationService.findById(paymentRequestDTO.reservationId()))
                .thenReturn(Optional.of(new Reservation(reservationId, userId, concertId, seatId, status, cost, reservationTime, expirationTime)));
        when(userService.findUserInfo(any())).thenReturn(new UserInfo(1L, userId, 60000L, LocalDateTime.now()));
        when(concertService.findByConcertId(any())).thenReturn(new Concert(concertId, "첫번째콘서트", LocalDateTime.now(), 50L, 25L, LocalDateTime.now()));

        PaymentResponseDTO actualValue = paymentUseCase.execute(paymentRequestDTO);

        assertEquals("결제 완료", actualValue.message());
        assertEquals(userId, actualValue.paymentInfoDTO().userId());
        assertEquals(reservationId, actualValue.paymentInfoDTO().reservationId());
        assertEquals(concertId, actualValue.paymentInfoDTO().concertId());
        assertEquals(seatId, actualValue.paymentInfoDTO().seatId());
        assertEquals(cost, actualValue.paymentInfoDTO().cost());
        assertEquals("reserved", actualValue.paymentInfoDTO().status());

    }

    @Test
    @DisplayName("결제 테스트 > 실패 > 포인트 부족")
    void paymentTest_lackOfPoint() {
        Long userId = 1L;
        Long reservationId = 1L;
        Long concertId = 1L;
        Long seatId = 1L;
        String status = "available";
        Long cost = 50000L;
        LocalDateTime reservationTime = LocalDateTime.now();
        LocalDateTime expirationTime = reservationTime.plusMinutes(5);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userId, reservationId);

        when(reservationService.findById(paymentRequestDTO.reservationId()))
                .thenReturn(Optional.of(new Reservation(reservationId, userId, concertId, seatId, status, cost, reservationTime, expirationTime)));
        when(userService.findUserInfo(any())).thenReturn(new UserInfo(1L, userId, 40000L, LocalDateTime.now()));
        when(concertService.findByConcertId(any())).thenReturn(new Concert(concertId, "첫번째콘서트", LocalDateTime.now(), 50L, 25L, LocalDateTime.now()));

        PaymentResponseDTO actualValue = paymentUseCase.execute(paymentRequestDTO);

        assertEquals("포인트가 부족합니다.", actualValue.message());
        assertEquals(null, actualValue.paymentInfoDTO());
    }

    @Test
    @DisplayName("결제 테스트 > 실패 > 해당 예약정보가 조회되지 않는경우")
    void paymentTest_reservationInfoEmpty() {
        Long userId = 1L;
        Long reservationId = 1L;
        LocalDateTime reservationTime = LocalDateTime.now();

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userId, reservationId);

        when(reservationService.findById(paymentRequestDTO.reservationId()))
                .thenReturn(Optional.empty());

        PaymentResponseDTO actualValue = paymentUseCase.execute(paymentRequestDTO);

        assertEquals("예약 정보가 없습니다.", actualValue.message());
        assertEquals(null, actualValue.paymentInfoDTO());
    }

    @Test
    @DisplayName("결제 테스트 > 실패 > 콘서트가 조회되지 않는경우")
    void paymentTest_UserInfoEmpty() {
        Long userId = 1L;
        Long reservationId = 1L;
        Long concertId = 1L;
        Long seatId = 1L;
        String status = "available";
        Long cost = 50000L;
        LocalDateTime reservationTime = LocalDateTime.now();
        LocalDateTime expirationTime = reservationTime.plusMinutes(5);

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userId, reservationId);

        when(reservationService.findById(paymentRequestDTO.reservationId()))
                .thenReturn(Optional.of(new Reservation(reservationId, userId, concertId, seatId, status, cost, reservationTime, expirationTime)));
        when(userService.findUserInfo(any())).thenReturn(new UserInfo(1L, userId, 40000L, LocalDateTime.now()));
        when(concertService.findByConcertId(any())).thenReturn(null);


        PaymentResponseDTO actualValue = paymentUseCase.execute(paymentRequestDTO);

        assertEquals("해당 콘서트 정보가 없습니다.", actualValue.message());
        assertEquals(null, actualValue.paymentInfoDTO());
    }
}

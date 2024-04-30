package org.example.ticketing.api.usecase.reservation;

import jakarta.transaction.Transactional;
import org.example.ticketing.api.dto.reservation.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.user.request.UserRequestDTO;
import org.example.ticketing.api.dto.reservation.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.user.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.concert.service.ConcertService;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.example.ticketing.domain.user.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MakeReservationUseCase {
    private final ReservationService reservationService;
    private final TokenService tokenService;
    private final ConcertService concertService;
    private final CheckTokenUseCase checkTokenUseCase;

    public MakeReservationUseCase(ReservationService reservationService, TokenService tokenService, ConcertService concertService, CheckTokenUseCase checkTokenUseCase) {
        this.reservationService = reservationService;
        this.tokenService = tokenService;
        this.concertService = concertService;
        this.checkTokenUseCase = checkTokenUseCase;
    }
    @Transactional
    public ReservationResponseDTO execute(ReservationRequestDTO reservationRequestDTO) {
        try {
            TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(new UserRequestDTO(reservationRequestDTO.userId()));
            String isValidToken = tokenResponseDTO.token();

            Concert concert = concertService.findByConcertId(reservationRequestDTO.concertId());
            if(concert == null) {
                return new ReservationResponseDTO("해당 콘서트는 존재하지 않습니다.", null);
            }
            if (isValidToken != null) {
                // 토큰이 유효한 경우, 좌석 예약 진행
                Reservation checkReservation = reservationService.findNonAvailableByConcertIdAndSeatId(reservationRequestDTO.concertId(), reservationRequestDTO.seatId());
                if(checkReservation != null){
                    return new ReservationResponseDTO("해당 좌석은 예약할 수 없습니다.", null);
                }
                Reservation reservation = reservationService.save(new Reservation(reservationRequestDTO.userId(), reservationRequestDTO.concertId(), reservationRequestDTO.seatId(), "temporary", reservationRequestDTO.cost(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(5)));
                if(reservation == null) {
                    return new ReservationResponseDTO("해당 좌석은 예약할 수 없습니다.", null);
                }
                tokenService.deleteByUserIdAndUseTrue(isValidToken, false);
                return new ReservationResponseDTO("좌석 예약 성공", reservation);
                // 좌석 예약 후 토큰 만료처리
            } else {
                // 토큰이 유효하지 않은 경우
                return new ReservationResponseDTO("토큰이 유효하지 않습니다.", null);
            }
        } catch (Exception e) {
            return new ReservationResponseDTO("예약 중 오류가 발생했습니다.", null);
        }
    }
}

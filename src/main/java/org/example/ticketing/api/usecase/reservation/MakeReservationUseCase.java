package org.example.ticketing.api.usecase.reservation;

import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ConcertResponseDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.common.ChangeSeatStatus;
import org.example.ticketing.api.usecase.user.CheckTokenUseCase;
import org.example.ticketing.domain.concert.model.Concert;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.example.ticketing.domain.reservation.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MakeReservationUseCase {
    private final ReservationService reservationService;
    private final CheckTokenUseCase checkTokenUseCase;

    public MakeReservationUseCase(ReservationService reservationService, CheckTokenUseCase checkTokenUseCase) {
        this.reservationService = reservationService;
        this.checkTokenUseCase = checkTokenUseCase;
    }

    public ReservationResponseDTO execute(ReservationRequestDTO reservationRequestDTO) {

//        try {
//            TokenResponseDTO tokenResponseDTO = checkTokenUseCase.execute(new UserRequestDTO(reservationRequestDTO.user_id()));
//            String isValidToken = tokenResponseDTO.token();
//
//            if (isValidToken != null) {
//                // 토큰이 유효한 경우, 콘서트 정보 조회
//                List<Concert> concertList = concertService.getConcertDate(LocalDateTime.now());
//                return new ConcertResponseDTO("이용가능한 콘서트 날짜 조회 성공", concertList);
//            } else {
//                // 토큰이 유효하지 않은 경우
//                return new ConcertResponseDTO("토큰이 유효하지 않습니다.", null);
//            }
//        } catch (Exception e) {
//            return new ConcertResponseDTO("콘서트 조회 중 오류가 발생했습니다.", null);
//        }

//        TokenResponseDTO tokenResponseDTO = confirmQueueUseCase.execute(userRequestDTO);
//        String checkToken = tokenResponseDTO.token().split("/")[1];
//
//        if (checkToken.equals("onGoing")) {
//            Reservation reservation = new Reservation(reservationRequestDTO.user_id(),
//                                        reservationRequestDTO.concert_id(),
//                                        reservationRequestDTO.seat_id(),
//                                        reservationRequestDTO.reservation_time(),
//                                        reservationRequestDTO.expiration_time());
//            Reservation reservedData = reservationRepository.reservationConcert(reservation);
//            Seat reservedSeat = changeSeatStatus.execute(reservationRequestDTO.seat_id(), "reserved");
//            updateTokenQueueStatus.execute(reservationRequestDTO.user_id());
//
//            return new ReservationResponseDTO(
//                    reservedData.getUserId(),
//                    reservedData.getConcertId(),
//                    reservedSeat.getSeatId(),
//                    reservedSeat.getSeatCost(),
//                    reservedSeat.getSeatStatus(),
//                    reservedData.getReservationTime(),
//                    reservedData.getExpirationTime());
//        } else {
//            String eMessage = "";
//            if(checkToken.equals("onWait")) {
//                eMessage = "현재 " + tokenResponseDTO.token().split("/")[2] + "명 대기상태 입니다.";
//            } else {
//                eMessage = "토큰이 만료되었습니다.";
//            }
//            throw new IllegalStateException(eMessage);
//        }
        return null;
    }
}

package org.example.ticketing.api.usecase.reservation;

import org.example.ticketing.api.dto.request.ReservationRequestDTO;
import org.example.ticketing.api.dto.request.UserRequestDTO;
import org.example.ticketing.api.dto.response.ReservationResponseDTO;
import org.example.ticketing.api.dto.response.TokenResponseDTO;
import org.example.ticketing.api.usecase.common.ChangeSeatStatus;
import org.example.ticketing.api.usecase.common.UpdateTokenQueueStatus;
import org.example.ticketing.api.usecase.user.ConfirmUserTokenUseCase;
import org.example.ticketing.domain.concert.model.Seat;
import org.example.ticketing.domain.concert.repository.ConcertRepository;
import org.example.ticketing.domain.reservation.model.Reservation;
import org.example.ticketing.domain.reservation.repository.ReservationRepository;
import org.example.ticketing.domain.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class MakeReservationUseCase {
    private ConfirmUserTokenUseCase confirmUserTokenUseCase;
    private ReservationRepository reservationRepository;
    private ChangeSeatStatus changeSeatStatus;
    private UpdateTokenQueueStatus updateTokenQueueStatus;

    public MakeReservationUseCase(ConfirmUserTokenUseCase confirmUserTokenUseCase, ReservationRepository reservationRepository, ChangeSeatStatus changeSeatStatus, UpdateTokenQueueStatus updateTokenQueueStatus) {
        this.confirmUserTokenUseCase = confirmUserTokenUseCase;
        this.reservationRepository = reservationRepository;
        this.changeSeatStatus = changeSeatStatus;
        this.updateTokenQueueStatus = updateTokenQueueStatus;
    }

    public ReservationResponseDTO execute(UserRequestDTO userRequestDTO, ReservationRequestDTO reservationRequestDTO) {
        TokenResponseDTO tokenResponseDTO = confirmUserTokenUseCase.execute(userRequestDTO);
        String checkToken = tokenResponseDTO.token().split("/")[1];

        if (checkToken.equals("onGoing")) {
            Reservation reservation = new Reservation(reservationRequestDTO.user_id(),
                                        reservationRequestDTO.concert_id(),
                                        reservationRequestDTO.seat_id(),
                                        reservationRequestDTO.reservation_time(),
                                        reservationRequestDTO.expiration_time());
            Reservation reservedData = reservationRepository.reservationConcert(reservation);
            Seat reservedSeat = changeSeatStatus.execute(reservationRequestDTO.seat_id(), "reserved");
            updateTokenQueueStatus.execute(reservationRequestDTO.user_id(), "expired");

            return new ReservationResponseDTO(
                    reservedData.getUserId(),
                    reservedData.getConcertId(),
                    reservedSeat.getSeatId(),
                    reservedSeat.getSeatCost(),
                    reservedSeat.getSeatStatus(),
                    reservedData.getReservationTime(),
                    reservedData.getExpirationTime());
        } else {
            String eMessage = "";
            if(checkToken.equals("onWait")) {
                eMessage = "현재 " + tokenResponseDTO.token().split("/")[2] + "명 대기상태 입니다.";
            } else {
                eMessage = "토큰이 만료되었습니다.";
            }
            throw new IllegalStateException(eMessage);
        }
    }
}
